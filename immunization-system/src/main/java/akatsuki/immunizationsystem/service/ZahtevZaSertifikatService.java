package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IZahtevZaSertifikatDAO;
import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
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
public class ZahtevZaSertifikatService {
    private final IZahtevZaSertifikatDAO zahtevZaSertifikatDAO;
    private final Validator validator;
    private final IModelMapper<ZahtevZaSertifikat> mapper;
    private final MetadataExtractor extractor;
    private final PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService;
    private final PdfTransformer pdfTransformer;
    private final HtmlTransformer htmlTransformer;
    private final DaoUtils utils;


    public String getZahtevZaSertifikat(String idBroj) throws RuntimeException {
        if (!validator.isIdValid(idBroj))
            throw new BadRequestRuntimeException("Id koji ste uneli nije validan.");
        ZahtevZaSertifikat zahtevZaSertifikat = zahtevZaSertifikatDAO.get(idBroj).orElseThrow(() -> new NotFoundRuntimeException("Zahtev za sertifikat sa id-jem " + idBroj + " nije pronadjena."));
        return mapper.convertToXml(zahtevZaSertifikat);
    }

    public int getResourcesCountInPeriod(String periodOd, String periodDo) {
        List<ZahtevZaSertifikat> allZahtevi = (List<ZahtevZaSertifikat>) zahtevZaSertifikatDAO.getAll();
        CalendarPeriod.calendarSetTimeByPeriod(periodOd, periodDo);
        int count = 0;
        for (ZahtevZaSertifikat z : allZahtevi) {
            Calendar datumPodnosenjaZahteva = z.getDatum().toGregorianCalendar();
            if (datumPodnosenjaZahteva.compareTo(CalendarPeriod.periodOdCal) > 0 && datumPodnosenjaZahteva.compareTo(CalendarPeriod.periodDoCal) < 0) {
                count++;
            }

        }
        return count;
    }

    public String createZahtevZaSertifikat(String zahtevXml) throws RuntimeException {
        ZahtevZaSertifikat zahtevZaSertifikat = mapper.convertToObject(zahtevXml);

        if (zahtevZaSertifikat == null || zahtevZaSertifikat.isOdobren())
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (zahtevZaSertifikatDAO.get(zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue()).isPresent())
            throw new NotFoundRuntimeException("Osoba sa id-jem " + zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue() + " je vec podnela zahtev.");

        if (!extractor.extractAndSaveToRdf(zahtevXml, "/zahtevi"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");

        setLinkToThisDocument(zahtevZaSertifikat);

        return zahtevZaSertifikatDAO.save(zahtevZaSertifikat);
    }

    private void setLinkToThisDocument(ZahtevZaSertifikat zahtevZaSertifikat) {
        potvrdaOIzvrsenojVakcinacijiService.setReference(zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue() + "_2",
                zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue());
    }

    public void setReference(String objectId, String referencedObjectId) {
        ZahtevZaSertifikat zahtevZaSertifikat = zahtevZaSertifikatDAO.get(objectId).get();
        zahtevZaSertifikat.setRel("pred:parentTo");
        zahtevZaSertifikat.setHref("http://www.akatsuki.org/digitalni-sertifikati/" + referencedObjectId);

        zahtevZaSertifikatDAO.save(zahtevZaSertifikat);
    }

    public ByteArrayInputStream generatePdf(String idBroj) {
        return pdfTransformer.generatePDF(getZahtevZaSertifikat(idBroj), ZahtevZaSertifikat.class);
    }

    public ByteArrayInputStream generateXhtml(String idBroj) {
        return htmlTransformer.generateHTML(getZahtevZaSertifikat(idBroj), ZahtevZaSertifikat.class);
    }


    public String getAllNeodobreni() {
        List<String> allNeodobreniZahtevi = utils.execute("//*[@odobren='false']", "/db/vaccination-system/zahtevi");
        StringBuilder str = new StringBuilder();
        str.append("<neodobreniZahteviDTO>");
        for (String zahtev: allNeodobreniZahtevi) {
            str.append(zahtev);
        }
        str.append("</neodobreniZahteviDTO>");
        return str.toString();
    }

    public void odobriZahtev(String idBroj) {
        ZahtevZaSertifikat zahtevZaSertifikat = zahtevZaSertifikatDAO.get(idBroj).get();
        zahtevZaSertifikat.setOdobren(true);

        zahtevZaSertifikatDAO.save(zahtevZaSertifikat);
    }

    public void odbaciZahtev(String idBroj) {
        utils.deleteResource("/db/vaccination-system/zahtevi", idBroj);
    }
  
    public MetadataDTO getMetadataJSON(String idBroj) {
        return new MetadataDTO("<http://www.akatsuki.org/zahtevi/" + idBroj + ">", extractor.readFromRdfWhereObjectIs("/zahtevi", idBroj));
    }

    public String getMetadataRDF(String idBroj) {
        return extractor.getRdfMetadata("/zahtevi", idBroj);
    }

    public String getZahteveBySearchInput(String searchInput) {
        List<String> zahtevi = zahtevZaSertifikatDAO.getAllXmls();
        StringBuilder str = new StringBuilder();
        str.append("<zahtevi>");
        for (String i: zahtevi) {
            if (i.contains(searchInput)) {
                String idBroj = i.split("about=\"http://www.akatsuki.org/zahtevi/")[1]
                        .split("\"")[0];
                str.append("</idBroj>").append(idBroj).append("</idBroj>");
            }
        }
        str.append("</zahtevi>");
        return str.toString();
    }
}
