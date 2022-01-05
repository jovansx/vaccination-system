package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IZahtevZaSertifikatDAO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.PdfTransformer;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class ZahtevZaSertifikatService {
    private final IZahtevZaSertifikatDAO zahtevZaSertifikatDAO;
    private final Validator validator;
    private final IModelMapper<ZahtevZaSertifikat> mapper;
    private final MetadataExtractor extractor;
    private final PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService;
    private final PdfTransformer pdfTransformer;


    public String getZahtevZaSertifikat(String idBroj) throws RuntimeException {
        if (!validator.isIdValid(idBroj))
            throw new BadRequestRuntimeException("Id koji ste uneli nije validan.");
        ZahtevZaSertifikat zahtevZaSertifikat = zahtevZaSertifikatDAO.get(idBroj).orElseThrow(() -> new NotFoundRuntimeException("Zahtev za sertifikat sa id-jem " + idBroj + " nije pronadjena."));
        return mapper.convertToXml(zahtevZaSertifikat);
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
}
