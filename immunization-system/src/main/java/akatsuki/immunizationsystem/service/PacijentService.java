package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.model.users.Korisnik;
import akatsuki.immunizationsystem.model.users.Pacijent;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacijentService {

    private final IDao<Korisnik> pacijentIDao;
    private final Validator validator;
    private final IModelMapper<Korisnik> mapper;
    private final PasswordEncoder encoder;


    public String createPacijenta(String pacijentXml) {
        Pacijent pacijent = (Pacijent) mapper.convertToObject(pacijentXml);
        if (!validator.isIdValid(pacijent.getIdBroj()))
            throw new BadRequestRuntimeException("Id koji ste uneli nije validan.");

        if (pacijentXml == null)
            throw new BadRequestRuntimeException("Dokument koji ste poslali nije validan.");

        if (pacijentIDao.get(pacijent.getIdBroj()).isPresent())
            throw new ConflictRuntimeException("Osoba s id-om " + pacijent.getIdBroj() + " je vec uneta u sistem.");

        pacijent.setSifra(encoder.encode(pacijent.getSifra()));

        return pacijentIDao.save(pacijent);
    }
}
