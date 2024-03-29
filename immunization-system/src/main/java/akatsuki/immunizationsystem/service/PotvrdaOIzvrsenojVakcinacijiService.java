package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.PotvrdaOVakcinaciji;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.dto.RaspodelaPoDozamaDTO;
import akatsuki.immunizationsystem.model.dto.RaspodelaPoProizvodjacimaDTO;
import akatsuki.immunizationsystem.model.vaccine.VaccineType;
import akatsuki.immunizationsystem.utils.*;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PotvrdaOIzvrsenojVakcinacijiService {
    private final IDao<PotvrdaOVakcinaciji> potvrdaOVakcinacijiIDao;
    private final Validator validator;
    private final IModelMapper<PotvrdaOVakcinaciji> mapper;
    private final IModelMapper<RaspodelaPoProizvodjacimaDTO> raspodelaPoProizvodjacimaDTOmapper;
    private final IModelMapper<RaspodelaPoDozamaDTO> raspodelaPoDozamaDTOmapper;
    private final MetadataExtractor extractor;
    private final SaglasnostZaImunizacijuService saglasnostZaImunizacijuService;
    private final RestTemplate restTemplate;
    private final PdfTransformer pdfTransformer;
    private final HtmlTransformer htmlTransformer;
    private final QRCodeGenerator qrCodeGenerator;
    private final DaoUtils utils;

    public static VaccineType getVaccineTypeFromStringValue(String givenName) {
        return Stream.of(VaccineType.values())
                .filter(direction -> direction.label.equals(givenName))
                .findFirst()
                .orElse(null);
    }

    public String getSerijuPrveVakcine(String idBrojDoza) {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(idBrojDoza).
                orElseThrow(() -> new NotFoundRuntimeException("Nije pronadjena potvrda sa unetim " + idBrojDoza + "."));
        return potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().get(0).getSerija();
    }

    public String getPotvrdaOIzvrsenojVakcinaciji(String idBrojDoza) throws RuntimeException {
        if (!validator.isIdDozaValid(idBrojDoza))
            throw new BadRequestRuntimeException("Id i doza " + idBrojDoza + " nije validan.");

        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(idBrojDoza).
                orElseThrow(() -> new NotFoundRuntimeException("Nije pronadjena potvrda sa unetim " + idBrojDoza + "."));
        return mapper.convertToXml(potvrdaOVakcinaciji);
    }

    public String getResourcesCountByDoze(String periodOd, String periodDo) {
        List<PotvrdaOVakcinaciji> allPotvrde = (List<PotvrdaOVakcinaciji>) potvrdaOVakcinacijiIDao.getAll();
        CalendarPeriod.calendarSetTimeByPeriod(periodOd, periodDo);
        int doza1 = 0;
        int doza2 = 0;
        for (PotvrdaOVakcinaciji p : allPotvrde) {
            Calendar datumIzdavanjaPotvrde = p.getDatumIzdavanja().toGregorianCalendar();
            if (datumIzdavanjaPotvrde.compareTo(CalendarPeriod.periodOdCal) > 0 && datumIzdavanjaPotvrde.compareTo(CalendarPeriod.periodDoCal) < 0) {
                if (p.getPrimljeneVakcine().getDoza().size() == 1) {
                    doza1++;
                } else {
                    doza2++;
                }

            }
        }
        RaspodelaPoDozamaDTO raspodelaPoDozamaDTO = new RaspodelaPoDozamaDTO(doza1, doza2);
        return raspodelaPoDozamaDTOmapper.convertToXml(raspodelaPoDozamaDTO);
    }

    public String getResourcesCountByProizvodjaci(String periodOd, String periodDo) {
        List<PotvrdaOVakcinaciji> allPotvrde = (List<PotvrdaOVakcinaciji>) potvrdaOVakcinacijiIDao.getAll();

        CalendarPeriod.calendarSetTimeByPeriod(periodOd, periodDo);

        int pfizerBioNTech = 0;
        int sputnikV = 0;
        int sinopharm = 0;
        int astraZeneca = 0;
        int moderna = 0;
        for (PotvrdaOVakcinaciji p : allPotvrde) {
            Calendar datumIzdavanjaPotvrde = p.getDatumIzdavanja().toGregorianCalendar();
            if (datumIzdavanjaPotvrde.compareTo(CalendarPeriod.periodOdCal) == 1 && datumIzdavanjaPotvrde.compareTo(CalendarPeriod.periodDoCal) == -1) {
                if (p.getNazivVakcine().getValue().equals("Pfizer-BioNTech")) {
                    pfizerBioNTech++;
                } else if (p.getNazivVakcine().getValue().equals("Sputnik V")) {
                    sputnikV++;
                } else if (p.getNazivVakcine().getValue().equals("Sinopharm")) {
                    sinopharm++;
                } else if (p.getNazivVakcine().getValue().equals("AstraZeneca")) {
                    astraZeneca++;
                } else {
                    moderna++;
                }
            }
        }
        RaspodelaPoProizvodjacimaDTO raspodelaPoProizvodjacimaDTO = new RaspodelaPoProizvodjacimaDTO(pfizerBioNTech, sputnikV, sinopharm, astraZeneca, moderna);
        return raspodelaPoProizvodjacimaDTOmapper.convertToXml(raspodelaPoProizvodjacimaDTO);
    }

    public String createPotvrdaOIzvrsenojVakcinaciji(String potvrdaOIzvrsenojVakcinacijiXml) throws RuntimeException {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = mapper.convertToObject(potvrdaOIzvrsenojVakcinacijiXml);
        if (potvrdaOVakcinaciji == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        int broj_doze = potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().size();
        String documentId = potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_" + broj_doze;
        if (potvrdaOVakcinacijiIDao.get(documentId).isPresent())
            throw new ConflictRuntimeException("Osoba s id-om " + potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue()
                    + " i dozom broj " + broj_doze + " ima vec kreiranu potvrdu.");

        if (!extractor.extractAndSaveToRdf(potvrdaOIzvrsenojVakcinacijiXml, "/potvrde"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        setLinkToThisDocument(potvrdaOVakcinaciji);

//        decreaseAmountOfVaccine(potvrdaOVakcinaciji);

        potvrdaOVakcinaciji.setQrCode(qrCodeGenerator.getQRCodeImage("http://localhost:8081/api/potvrde/pdf/" + documentId));
        return potvrdaOVakcinacijiIDao.save(potvrdaOVakcinaciji);
    }

    private void decreaseAmountOfVaccine(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {
        VaccineType vaccine = getVaccineTypeFromStringValue(potvrdaOVakcinaciji.getNazivVakcine().getValue());

        int dozesSize = potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().size();

        restTemplate.put("http://localhost:8083/api/vakcine/" + vaccine.name() + "/" +
                potvrdaOVakcinaciji.getPrimljeneVakcine().getDoza().get(dozesSize - 1).getSerija(), null);
    }

    private void setLinkToThisDocument(PotvrdaOVakcinaciji potvrdaOVakcinaciji) {
        try {
            saglasnostZaImunizacijuService.getSaglasnostZaImunizaciju(
                    potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_2");
            saglasnostZaImunizacijuService.setReference(potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_2",
                    potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_2");
        } catch (Exception ignored) {
            saglasnostZaImunizacijuService.setReference(potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_1",
                    potvrdaOVakcinaciji.getPrimalac().getIdBroj().getValue() + "_1");
        }
    }

    public void setReference(String objectId, String referencedObjectId) {
        PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(objectId).get();

        potvrdaOVakcinaciji.setRel("pred:parentTo");

        if (!referencedObjectId.contains("_"))
            potvrdaOVakcinaciji.setHref("http://www.akatsuki.org/zahtevi/" + referencedObjectId);
        else
            potvrdaOVakcinaciji.setHref("http://www.akatsuki.org/saglasnosti/" + referencedObjectId);

        potvrdaOVakcinacijiIDao.save(potvrdaOVakcinaciji);
    }

    public ByteArrayInputStream generatePdf(String idBroj) {
        return pdfTransformer.generatePDF(getPotvrdaOIzvrsenojVakcinaciji(idBroj), PotvrdaOVakcinaciji.class);
    }

    public ByteArrayInputStream generateXhtml(String idBroj) {
        return htmlTransformer.generateHTML(getPotvrdaOIzvrsenojVakcinaciji(idBroj), PotvrdaOVakcinaciji.class);
    }

    public String getDrugePotvrde() {
        List<String> allDrugePotvrde = utils.execute("//*[@about[contains(.,'_2')]]", "/db/vaccination-system/potvrde");
        StringBuilder str = new StringBuilder();
        str.append("<drugePotvrdeDTO>");
        for (String potvrda: allDrugePotvrde) {
            str.append(potvrda);
        }
        str.append("</drugePotvrdeDTO>");
        return str.toString();
    }

    public MetadataDTO getMetadataJSON(String idBrojIndex) {
        return new MetadataDTO("<http://www.akatsuki.org/potvrde/" + idBrojIndex + ">", extractor.readFromRdfWhereObjectIs("/potvrde", idBrojIndex));
    }

    public String getMetadataRDF(String idBrojIndex) {
        return extractor.getRdfMetadata("/potvrde", idBrojIndex);
    }

    public String getPotvrdeBySearchInput(String searchInput) {
        List<String> potvrde = potvrdaOVakcinacijiIDao.getAllXmls();
        StringBuilder str = new StringBuilder();
        str.append("<potvrde>");
        for (String i: potvrde) {
            if (i.contains(searchInput)  || searchInput.equals("null")) {
                String idBroj = i.split("about=\"http://www.akatsuki.org/potvrde/")[1]
                        .split("\"")[0];
                str.append("<idBroj>").append(idBroj).append("</idBroj>");
            }
        }
        str.append("</potvrde>");
        return str.toString();
    }

    public String getPotvrdeAdvenced(String ime, String prezime, String id_broj) {
        String condition = "";
        if(!ime.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/ime> \""+ime+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!prezime.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/prezime> \""+prezime+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!id_broj.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/id_broj> \""+id_broj+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(condition.equals(""))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/id_broj> ?o";

        StringBuilder str = new StringBuilder();
        str.append("<potvrde>");
        for (String i: extractor.filterFromRdf("/potvrde", condition)) {
            String idBroj = i.split("http://www.akatsuki.org/potvrde/")[1];

            str.append("<potvrda>");

            str.append("<idBroj>").append(idBroj).append("</idBroj>");

            try {
                PotvrdaOVakcinaciji potvrdaOVakcinaciji = potvrdaOVakcinacijiIDao.get(idBroj).
                        orElseThrow(() -> new NotFoundRuntimeException("Nije pronadjena potvrda sa unetim " + idBroj + "."));
                str.append("<parentTo>").append(potvrdaOVakcinaciji.getHref()).append("</parentTo>");
            } catch (Exception ignored) {}

            str.append("</potvrda>");
        }
        str.append("</potvrde>");
        return str.toString();
    }
}
