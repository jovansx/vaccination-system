package akatsuki.officialsystem.config;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.model.documents.IzvestajOImunizaciji;
import akatsuki.officialsystem.model.types.TCBrojInteresovanja;
import akatsuki.officialsystem.model.types.TCIzdatoZelenih;
import akatsuki.officialsystem.model.types.TCPrimljenoZelenih;
import akatsuki.officialsystem.model.users.Sluzbenik;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.model.vaccine.VaccineType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Configuration
@Slf4j
public class DbConfiguration {

    @Bean
    public CommandLineRunner populateDatabase(IDao<Sluzbenik> sluzbenikIDao, IDao<Vaccine> vaccineIDao) {
        return (args) -> {

            Sluzbenik sluzbenik = new Sluzbenik("1010998800070", "Marko",
                    "Markovic", "(021) 823-1112", "marko@gmail.com", "$2a$12$GfYvRDS42Ki3Dk3w39svpeKozJOFYPFYxwgP6l0eEyuadTf5gE5Ry");

            sluzbenikIDao.save(sluzbenik);

            Vaccine.Serie serie01 = new Vaccine.Serie(100L, 1L);
            Vaccine.Serie serie02 = new Vaccine.Serie(100L, 2L);
            Vaccine.Serie serie11 = new Vaccine.Serie(100L, 1L);
            Vaccine.Serie serie12 = new Vaccine.Serie(100L, 2L);
            Vaccine.Serie serie21 = new Vaccine.Serie(100L, 1L);
            Vaccine.Serie serie22 = new Vaccine.Serie(100L, 2L);
            Vaccine.Serie serie31 = new Vaccine.Serie(100L, 1L);
            Vaccine.Serie serie32 = new Vaccine.Serie(100L, 2L);
            Vaccine.Serie serie41 = new Vaccine.Serie(100L, 1L);
            Vaccine.Serie serie42 = new Vaccine.Serie(100L, 2L);

            Vaccine v1 = new Vaccine(VaccineType.MODERNA, "Temperatura", "Britanski proizvodjac", List.of(serie01, serie02));
            Vaccine v2 = new Vaccine(VaccineType.ASTRA_ZENECA, "Temperatura", "Britanski proizvodjac", List.of(serie11, serie12));
            Vaccine v3 = new Vaccine(VaccineType.PFIZER,  "Temperatura", "Americki proizvodjac", List.of(serie21, serie22));
            Vaccine v4 = new Vaccine(VaccineType.SINOPHARM, "Temperatura", "Kineski proizvodjac", List.of(serie31, serie32));
            Vaccine v5 = new Vaccine(VaccineType.SPUTNIK_V, "Temperatura", "Ruski proizvodjac", List.of(serie41, serie42));

            vaccineIDao.save(v1);
            vaccineIDao.save(v2);
            vaccineIDao.save(v3);
            vaccineIDao.save(v4);
            vaccineIDao.save(v5);

//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            GregorianCalendar calendar1 = new GregorianCalendar();
//            GregorianCalendar calendar2 = new GregorianCalendar();
//            Date dateFrom1 = format.parse("2020-01-01");
//            Date dateTo1 = format.parse("2020-01-31");
//            calendar1.setTime(dateFrom1);
//            calendar2.setTime(dateTo1);
//
//            IzvestajOImunizaciji.Period period1 = new IzvestajOImunizaciji.Period(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar1), DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
//
//            dateFrom1 = format.parse("2020-02-01");
//            dateTo1 = format.parse("2020-02-28");
//            calendar1.setTime(dateFrom1);
//            calendar2.setTime(dateTo1);
//
//            IzvestajOImunizaciji.Period period2 = new IzvestajOImunizaciji.Period(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar1), DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
//
//            dateFrom1 = format.parse("2020-03-01");
//            dateTo1 = format.parse("2020-03-31");
//            calendar1.setTime(dateFrom1);
//            calendar2.setTime(dateTo1);
//
//            IzvestajOImunizaciji.Period period3 = new IzvestajOImunizaciji.Period(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar1), DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
//
//            dateFrom1 = format.parse("2020-04-01");
//            dateTo1 = format.parse("2020-04-30");
//            calendar1.setTime(dateFrom1);
//            calendar2.setTime(dateTo1);
//
//            IzvestajOImunizaciji.Period period4 = new IzvestajOImunizaciji.Period(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar1), DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
//
//            dateFrom1 = format.parse("2020-05-01");
//            dateTo1 = format.parse("2020-05-31");
//            calendar1.setTime(dateFrom1);
//            calendar2.setTime(dateTo1);
//
//            IzvestajOImunizaciji.Period period5 = new IzvestajOImunizaciji.Period(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar1), DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar2));
//            IzvestajOImunizaciji.Dokumenti.ZeleniSertifikat zeleni1 = new IzvestajOImunizaciji.Dokumenti.ZeleniSertifikat(new TCPrimljenoZelenih(), new TCIzdatoZelenih());
//            IzvestajOImunizaciji.Dokumenti dokumenti1 = new IzvestajOImunizaciji.Dokumenti(new TCBrojInteresovanja(), zeleni1);
//            IzvestajOImunizaciji.DozeVakcina doze1 = new IzvestajOImunizaciji.DozeVakcina(new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoDozama(), new IzvestajOImunizaciji.DozeVakcina.RaspodelaPoProizvodjacima(), 20);
//
//            IzvestajOImunizaciji izvestaj1 = new IzvestajOImunizaciji(period5, dokumenti1, doze1, DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar1), "http://www.akatsuki.org/izvestaji/2020-01-01_2020-01-31");

            log.info("Database is populated");
        };
    }
}
