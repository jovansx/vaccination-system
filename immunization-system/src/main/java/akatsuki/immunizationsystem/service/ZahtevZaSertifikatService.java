package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.ISaglasnostZaImunizacijuDAO;
import akatsuki.immunizationsystem.dao.IZahtevZaSertifikatDAO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZahtevZaSertifikatService {
    private final IZahtevZaSertifikatDAO zahtevZaSertifikatDAO;
    private final Validator validator;
    private final IModelMapper<ZahtevZaSertifikat> mapper;

    public String getZahtevZaSertifikat(String jmbg, UUID id) throws RuntimeException {
        if (!validator.isJmbgValid(jmbg))
            throw new BadRequestRuntimeException("Jmbg koji ste uneli nije validan.");
        String documentId = jmbg + "_" + id;
        ZahtevZaSertifikat zahtevZaSertifikat = zahtevZaSertifikatDAO.get(documentId).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + id + " nije pronadjena."));
        return mapper.convertToXml(zahtevZaSertifikat);
    }
}
