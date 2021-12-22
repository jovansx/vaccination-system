package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.DigitalniSertifikat;
import akatsuki.immunizationsystem.utils.MetadataExtractor;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DigitalniSertifikatService {
    private final IDao<DigitalniSertifikat> digitalniSertifikatDAO;
    private final Validator validator;
    private final IModelMapper<DigitalniSertifikat> mapper;
    private final MetadataExtractor extractor;

    public String getDigitalniSertifikat(String jmbg) throws RuntimeException {
        if (!validator.isJmbgValid(jmbg))
            throw new BadRequestRuntimeException("Jmbg koji ste uneli nije validan.");

        DigitalniSertifikat digitalniSertifikat = digitalniSertifikatDAO.get(jmbg).orElseThrow(() -> new NotFoundRuntimeException("Osoba s jmbg-om " + jmbg + " nema digitalni zeleni sertifikat."));
        return mapper.convertToXml(digitalniSertifikat);
    }

    public String createDigitalniSertifikat(String digitalniSertifikatXml) throws RuntimeException {
        DigitalniSertifikat digitalniSertifikat = mapper.convertToObject(digitalniSertifikatXml);
        if (digitalniSertifikat == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (digitalniSertifikatDAO.get(digitalniSertifikat.getPrimalac().getJmbg().getValue()).isPresent())
            throw new ConflictRuntimeException("Osoba s jmbg-om " + digitalniSertifikat.getPrimalac().getJmbg().getValue() + " vec ima digitalni zeleni sertifikat.");

        if (!extractor.extractAndSaveToRdf(digitalniSertifikatXml, "/digitalni-sertifikati"))
            throw new BadRequestRuntimeException("Ekstrakcija metapodataka nije uspela.");
        return digitalniSertifikatDAO.save(digitalniSertifikat);
    }

}
