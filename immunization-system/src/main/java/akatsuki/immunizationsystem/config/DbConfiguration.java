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
import java.util.Calendar;
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

            korisnikIDao.save(doktor1);
            korisnikIDao.save(pacijent1);

            DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//            date = format2.parse("2022-01-05 08:00");
//            TODO - iznad 2 linije zameniti sa date = new Date(); kad bude trebalo
            calendar.setTime(new Date());
            Appointment appointment1 = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), "1010998800070", true);
            appointmentIDao.save(appointment1);

//            calendar.add(Calendar.MINUTE, Appointment.DURATION_IN_MINUTES);
//            Appointment appointment2 = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), "1010998800070", true);
//            appointmentIDao.save(appointment2);
//
//            calendar.add(Calendar.MINUTE, Appointment.DURATION_IN_MINUTES);
//            Appointment appointment3 = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), "1010998800070", false);
//            appointmentIDao.save(appointment3);
//
//            calendar.add(Calendar.MINUTE, Appointment.DURATION_IN_MINUTES);
//            Appointment appointment4 = new Appointment(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar), "1010998800070", false);
//            appointmentIDao.save(appointment4);

            log.info("Database is populated");
        };
    }
}
