package akatsuki.officialsystem.config;

import akatsuki.officialsystem.dao.IDao;
import akatsuki.officialsystem.model.users.Sluzbenik;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DbConfiguration {

    @Bean
    public CommandLineRunner populateDatabase(IDao<Sluzbenik> sluzbenikIDao) {
        return (args) -> {

            Sluzbenik sluzbenik = new Sluzbenik("1010998800070", "Marko",
                    "Markovic", "(021) 823-1112", "marko@gmail.com", "$2a$12$GfYvRDS42Ki3Dk3w39svpeKozJOFYPFYxwgP6l0eEyuadTf5gE5Ry");

            sluzbenikIDao.save(sluzbenik);

            log.info("Database is populated");
        };
    }
}
