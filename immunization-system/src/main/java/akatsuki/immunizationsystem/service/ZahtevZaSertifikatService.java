package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IZahtevZaSertifikatDAO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
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

    public String getZahtevZaSertifikat(String idBrojIndex) throws RuntimeException {
        if (!validator.isIdDozaValid(idBrojIndex))
            throw new BadRequestRuntimeException("Id koji ste uneli nije validan.");
        ZahtevZaSertifikat zahtevZaSertifikat = zahtevZaSertifikatDAO.get(idBrojIndex).orElseThrow(() -> new NotFoundRuntimeException("Saglasnost sa id-jem " + idBrojIndex + " nije pronadjena."));
        return mapper.convertToXml(zahtevZaSertifikat);
    }

    public String createZahtevZaSertifikat(String zahtevXml) throws RuntimeException {
        ZahtevZaSertifikat zahtevZaSertifikat = mapper.convertToObject(zahtevXml);

        if (zahtevZaSertifikat == null || zahtevZaSertifikat.isOdobren())
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if(zahtevZaSertifikatDAO.getByIdBroj(zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue()).isPresent())
            throw new BadRequestRuntimeException("Osoba s id-om " + zahtevZaSertifikat.getPodnosilac().getIdBroj().getValue() + " je vec podnela zahtev za sertifikat.");

        return zahtevZaSertifikatDAO.save(zahtevZaSertifikat);
    }
}
