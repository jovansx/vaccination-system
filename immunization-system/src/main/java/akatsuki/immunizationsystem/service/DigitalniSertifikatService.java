package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.DigitalniSertifikat;
import akatsuki.immunizationsystem.model.documents.Interesovanje;
import akatsuki.immunizationsystem.utils.*;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

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
}
