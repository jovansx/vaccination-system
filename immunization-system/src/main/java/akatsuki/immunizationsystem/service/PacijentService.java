package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.users.Korisnik;
import akatsuki.immunizationsystem.model.users.Pacijent;
import akatsuki.immunizationsystem.model.users.enums.TipKorisnika;
import akatsuki.immunizationsystem.utils.Validator;
import akatsuki.immunizationsystem.utils.modelmappers.IModelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacijentService {

    private final IDao<Korisnik> pacijentIDao;
    private final Validator validator;
    private final IModelMapper<Korisnik> mapper;
    private final PasswordEncoder encoder;
    private final DaoUtils utils;

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

    public String getTrenutnaForma(String idBroj) {
        // TODO: Kasnije dodaj komplikovaniju logiku

        List<String> retVal = utils.execute("//*[text() = '" + idBroj + "']", "/db/vaccination-system/interesovanja");
        if (retVal.size() == 0) return "interesovanje";

        retVal = utils.execute("//*[text() = '" + idBroj + "']", "/db/vaccination-system/saglasnosti");
        if (retVal.size() == 0) return "saglasnost-1";

        List<String> potvrde = new ArrayList<>();
        if (retVal.size() == 1) {
            potvrde = utils.execute("//*[text() = '" + idBroj + "']", "/db/vaccination-system/potvrde");
            if (potvrde.size() == 1) return "saglasnost-2";
            if (potvrde.size() == 0) return "nista";
        }

        retVal = utils.execute("//*[text() = '" + idBroj + "']", "/db/vaccination-system/zahtevi");
        if (retVal.size() == 0 && potvrde.size() == 2) return "zahtev";

        return "nista";
    }

    public Pacijent getPacijent(String idBroj) {
        Optional<Korisnik> korisnik = pacijentIDao.get(idBroj);
        if (korisnik.isEmpty())
            throw new NotFoundRuntimeException("Osoba s id-om " + idBroj + " ne postoji.");
        if (korisnik.get().getTip().equals(TipKorisnika.DOKTOR))
            throw new ConflictRuntimeException("Osoba s id-om " + idBroj + " nije validna.");
        return (Pacijent) korisnik.get();
    }
}
