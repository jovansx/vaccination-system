package akatsuki.immunizationsystem.config;

import akatsuki.immunizationsystem.utils.DbConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

    @Value("${db.username}")
    private String username;

    @Value("${db.password}")
    private String password;

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.driver}")
    private String dbDriver;

    @Bean
    DbConnection dbConnection() {
        return new DbConnection(username, password, dbUrl, dbDriver);
    }
}
