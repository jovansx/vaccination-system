package akatsuki.officialsystem.config;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.model.users.Sluzbenik;
import akatsuki.officialsystem.model.vaccine.Vaccine;
import akatsuki.officialsystem.model.vaccine.VaccineType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

            log.info("Database is populated");
        };
    }
}
