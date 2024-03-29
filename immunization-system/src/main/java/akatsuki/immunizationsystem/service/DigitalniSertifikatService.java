package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.DigitalniSertifikat;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.*;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DigitalniSertifikatService {
    private final IDao<DigitalniSertifikat> digitalniSertifikatDAO;
    private final Validator validator;
    private final IModelMapper<DigitalniSertifikat> mapper;
    private final MetadataExtractor extractor;
    private final ZahtevZaSertifikatService zahtevZaSertifikatService;
    private final PdfTransformer pdfTransformer;
    private final HtmlTransformer htmlTransformer;
    private final QRCodeGenerator qrCodeGenerator;

    public String getDigitalniSertifikat(String idBroj) throws RuntimeException {
        if (!validator.isIdValid(idBroj))
            throw new BadRequestRuntimeException("Identifikacioni broj koji ste uneli nije validan.");

        DigitalniSertifikat digitalniSertifikat = digitalniSertifikatDAO.get(idBroj).orElseThrow(() -> new NotFoundRuntimeException("Osoba s id-om " + idBroj + " nema digitalni zeleni sertifikat."));
        return mapper.convertToXml(digitalniSertifikat);
    }

    public int getResourcesCountInPeriod(String periodOd, String periodDo) {
        List<DigitalniSertifikat> allSertifikati = (List<DigitalniSertifikat>) digitalniSertifikatDAO.getAll();
        CalendarPeriod.calendarSetTimeByPeriod(periodOd, periodDo);
        int count = 0;
        for (DigitalniSertifikat d : allSertifikati) {
            Calendar datumIzdavanjaSertifikata = d.getDatumIVremeIzdavanja().toGregorianCalendar();
            if (datumIzdavanjaSertifikata.compareTo(CalendarPeriod.periodOdCal) > 0 && datumIzdavanjaSertifikata.compareTo(CalendarPeriod.periodDoCal) < 0) {
                count++;
            }

        }
        return count;
    }

    public String createDigitalniSertifikat(String digitalniSertifikatXml) throws RuntimeException {
        DigitalniSertifikat digitalniSertifikat = mapper.convertToObject(digitalniSertifikatXml);

        if (digitalniSertifikat == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (digitalniSertifikatDAO.get(digitalniSertifikat.getPrimalac().getIdBroj().getValue()).isPresent())
            throw new ConflictRuntimeException("Osoba s id-om " + digitalniSertifikat.getPrimalac().getIdBroj().getValue() + " vec ima digitalni zeleni sertifikat.");

        if (!extractor.extractAndSaveToRdf(digitalniSertifikatXml, "/digitalni-sertifikati"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        setLinkToThisDocument(digitalniSertifikat);

        digitalniSertifikat.setQrCode(qrCodeGenerator.getQRCodeImage("http://localhost:8081/api/digitalni-sertifikati/pdf/"
                + digitalniSertifikat.getPrimalac().getIdBroj().getValue()));
        return digitalniSertifikatDAO.save(digitalniSertifikat);
    }

    private void setLinkToThisDocument(DigitalniSertifikat digitalniSertifikat) {
        zahtevZaSertifikatService.setReference(digitalniSertifikat.getPrimalac().getIdBroj().getValue(),
                digitalniSertifikat.getPrimalac().getIdBroj().getValue());
    }

    public ByteArrayInputStream generatePdf(String idBroj) {
        return pdfTransformer.generatePDF(getDigitalniSertifikat(idBroj), DigitalniSertifikat.class);
    }

    public ByteArrayInputStream generateXhtml(String idBroj) {
        return htmlTransformer.generateHTML(getDigitalniSertifikat(idBroj), DigitalniSertifikat.class);
    }

    public MetadataDTO getMetadataJSON(String idBroj) {
        return new MetadataDTO("<http://www.akatsuki.org/digitalni-sertifikati/" + idBroj + ">", extractor.readFromRdfWhereObjectIs("/digitalni-sertifikati", idBroj));
    }

    public String getMetadataRDF(String idBroj) {
        return extractor.getRdfMetadata("/digitalni-sertifikati", idBroj);
    }

    public String getSertifikatiBySearchInput(String searchInput) {
        List<String> sertifikati = digitalniSertifikatDAO.getAllXmls();
        StringBuilder str = new StringBuilder();
        str.append("<sertifikati>");
        for (String i: sertifikati) {
            if (i.contains(searchInput) || searchInput.equals("null")) {
                String idBroj = i.split("about=\"http://www.akatsuki.org/digitalni-sertifikati/")[1]
                        .split("\"")[0];
                str.append("<idBroj>").append(idBroj).append("</idBroj>");
            }
        }
        str.append("</sertifikati>");
        return str.toString();
    }

    public String getDigitalniSertifikateAdvenced(String ime, String prezime, String id_broj, String pol) {
        String condition = "";
        if(!ime.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/ime> \""+ime+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!prezime.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/prezime> \""+prezime+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!id_broj.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/id_broj> \""+id_broj+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(!pol.equals("null"))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/pol> \""+pol+"\"^^<http://www.w3.org/1999/02/22-rdf-syntax-ns#XMLLiteral> .";
        if(condition.equals(""))
            condition += "?s <http://www.akatsuki.org/rdf/examples/predicate/id_broj> ?o";

        StringBuilder str = new StringBuilder();
        str.append("<sertifikati>");
        for (String i: extractor.filterFromRdf("/digitalni-sertifikati", condition)) {
            String idBroj = i.split("http://www.akatsuki.org/digitalni-sertifikati/")[1];
            str.append("<sertifikat>");
            str.append("<idBroj>").append(idBroj).append("</idBroj>");
            str.append("</sertifikat>");
        }
        str.append("</sertifikati>");
        return str.toString();
    }
}
