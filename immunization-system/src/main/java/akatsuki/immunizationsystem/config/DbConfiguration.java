package akatsuki.immunizationsystem.config;

import akatsuki.immunizationsystem.dao.IDao;
import akatsuki.immunizationsystem.model.appointments.Appointment;
import akatsuki.immunizationsystem.model.users.Doktor;
import akatsuki.immunizationsystem.model.users.Korisnik;
import akatsuki.immunizationsystem.model.users.Pacijent;
import akatsuki.immunizationsystem.model.users.enums.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.DatatypeFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

@Configuration
@Slf4j
public class DbConfiguration {

    @Bean
    public CommandLineRunner populateDatabase(IDao<Korisnik> korisnikIDao, IDao<Appointment> appointmentIDao) {
        return (args) -> {
            Doktor doktor1 = new Doktor("Promenada u Novom Sadu", 1, "1010998800071", "Nikola",
                    "Nikolic", "(021) 823-1111", "nikola@gmail.com", "$2a$12$CLdgTeAs50lbVLOBELp7yele07svd3/1nNGefNJ6Lb5Qx/08eBMFa", TipKorisnika.DOKTOR);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            GregorianCalendar calendar = new GregorianCalendar();
            Date date = format.parse("1999-09-09");
            calendar.setTime(date);

            Pacijent pacijent1 = new Pacijent("1010998800070", "Marko",
                    "Markovic", "(021) 823-1112", "marko@gmail.com", "$2a$12$GfYvRDS42Ki3Dk3w39svpeKozJOFYPFYxwgP6l0eEyuadTf5gE5Ry",
                    TipKorisnika.PACIJENT, Pol.MUSKI, DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), TipDrzavljanstva.SRPSKO,
                    "Novi Sad", "Veternik", "Vladike Maksima", "12",
                    "0648736578", "Lazar", "Sombor", RadniStatus.STUDENT, Zanimanje.PROSVETA);

            Pacijent pacijent2 = new Pacijent("101099880", "Marko2",
                    "Markovic", "(021) 823-1113", "marko2@gmail.com", "$2a$12$GfYvRDS42Ki3Dk3w39svpeKozJOFYPFYxwgP6l0eEyuadTf5gE5Ry",
                    TipKorisnika.PACIJENT, Pol.MUSKI, DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), TipDrzavljanstva.STRANO_SA_BORAVKOM,
                    "Novi Sad", "Veternik", "Vladike Maksima", "11",
                    "0648736579", "Milorad", "Sombor", RadniStatus.STUDENT, Zanimanje.PROSVETA);

            Pacijent pacijent3 = new Pacijent("101099881", "Marko3",
                    "Markovic", "(021) 823-1113", "marko3@gmail.com", "$2a$12$GfYvRDS42Ki3Dk3w39svpeKozJOFYPFYxwgP6l0eEyuadTf5gE5Ry",
                    TipKorisnika.PACIJENT, Pol.MUSKI, DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), TipDrzavljanstva.STRANO_BEZ_BORAVKA,
                    "Novi Sad", "Veternik", "Vladike Maksima", "11",
                    "0648736519", "Milorad", "Sombor", RadniStatus.STUDENT, Zanimanje.PROSVETA);

            korisnikIDao.save(doktor1);
            korisnikIDao.save(pacijent1);
            korisnikIDao.save(pacijent2);
            korisnikIDao.save(pacijent3);

            calendar.setTime(new Date());
            Appointment appointment1 = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), "1010998800070", true, 2);
            appointmentIDao.save(appointment1);

            log.info("Database is populated");
        };
    }
}
