package akatsuki.immunizationsystem.service;

import akatsuki.immunizationsystem.dao.DaoUtils;
import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.dtos.DokumentIdDTO;
import akatsuki.immunizationsystem.exceptions.BadRequestRuntimeException;
import akatsuki.immunizationsystem.exceptions.ConflictRuntimeException;
import akatsuki.immunizationsystem.exceptions.NotFoundRuntimeException;
import akatsuki.immunizationsystem.model.documents.*;
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
    private final IDao<Interesovanje> interesovanjeIDao;
    private final IDao<SaglasnostZaImunizaciju> saglasnostZaImunizacijuIDao;
    private final IDao<PotvrdaOVakcinaciji> potvrdaOVakcinacijiIDao;
    private final IDao<ZahtevZaSertifikat> zahtevZaSertifikatIDao;
    private final IDao<DigitalniSertifikat> digitalniSertifikatIDao;

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

        List<String> potvrde = utils.execute("//*[text() = '" + idBroj + "']", "/db/vaccination-system/potvrde");
        if (retVal.size() == 1) {
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

    public List<DokumentIdDTO> getPatientDocuments(String idBroj) {
        List<DokumentIdDTO> retVal = new ArrayList<>();
        Optional<Interesovanje> interesovanje = interesovanjeIDao.get(idBroj);
        if (interesovanje.isPresent())
            retVal.add(new DokumentIdDTO("Interesovanje za vakcinacijom", idBroj));
        Optional<SaglasnostZaImunizaciju> saglasnost1 = saglasnostZaImunizacijuIDao.get(idBroj + "_1");
        if (saglasnost1.isPresent())
            retVal.add(new DokumentIdDTO("Saglasnost o imunizaciji 1", idBroj + "_1"));
        Optional<SaglasnostZaImunizaciju> saglasnost2 = saglasnostZaImunizacijuIDao.get(idBroj + "_2");
        if (saglasnost2.isPresent())
            retVal.add(new DokumentIdDTO("Saglasnost o imunizaciji 2", idBroj + "_2"));
        Optional<PotvrdaOVakcinaciji> potvrda1 = potvrdaOVakcinacijiIDao.get(idBroj + "_1");
        if (potvrda1.isPresent())
            retVal.add(new DokumentIdDTO("Potvrda o vakcinaciji 1", idBroj + "_1"));
        Optional<PotvrdaOVakcinaciji> potvrda2 = potvrdaOVakcinacijiIDao.get(idBroj + "_2");
        if (potvrda2.isPresent())
            retVal.add(new DokumentIdDTO("Potvrda o vakcinaciji 2", idBroj + "_2"));
        Optional<ZahtevZaSertifikat> zahtev = zahtevZaSertifikatIDao.get(idBroj);
        if (zahtev.isPresent())
            retVal.add(new DokumentIdDTO("Zahtev za sertifikat", idBroj));
        Optional<DigitalniSertifikat> sertifikat = digitalniSertifikatIDao.get(idBroj);
        if (sertifikat.isPresent())
            retVal.add(new DokumentIdDTO("Zeleni sertifikat", idBroj));
        return retVal;
    }
}
