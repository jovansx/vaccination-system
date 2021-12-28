package akatsuki.officialsystem.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RdfConnection {

    @Value("${rdf.endpoint}")
    private String endpoint;

    @Value("${rdf.dataset}")
    private String dataset;

    @Value("${rdf.query}")
    private String query;

    @Value("${rdf.update}")
    private String update;

    @Value("${rdf.data}")
    private String data;
}
