package akatsuki.officialsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PretragaService {

    private final RestTemplate template;

    public String basicSearchDocuments(String searchInput) {
        ResponseEntity<String> interesovanjaRetVal =
                template.getForEntity("http://localhost:8081/api/interesovanja/pretrazi/" + searchInput, String.class);
        ResponseEntity<String> potvrdeRetVal =
                template.getForEntity("http://localhost:8081/api/potvrde/pretrazi/" + searchInput, String.class);
        ResponseEntity<String> saglasnostiRetVal =
                template.getForEntity("http://localhost:8081/api/saglasnosti/pretrazi/" + searchInput, String.class);
        ResponseEntity<String> zahteviRetVal =
                template.getForEntity("http://localhost:8081/api/zahtevi/pretrazi/" + searchInput, String.class);
        ResponseEntity<String> sertifikatiRetVal =
                template.getForEntity("http://localhost:8081/api/digitalni-sertifikati/pretrazi/" + searchInput, String.class);

        return "<dokumenti>" +
                interesovanjaRetVal.getBody() +
                potvrdeRetVal.getBody() +
                saglasnostiRetVal.getBody() +
                zahteviRetVal.getBody() +
                sertifikatiRetVal.getBody() +
                "</dokumenti>";

    }

    public String getInteresovanjeAdvenced(String ime, String prezime, String id_broj, String lokacija) {
        ResponseEntity<String> interesovanjaRetVal =
                template.getForEntity("http://localhost:8081/api/interesovanja/napredno-pretrazi?ime=" + ime + "&prezime="+ prezime + "&id_broj=" + id_broj + "&lokacija=" + lokacija, String.class);
        return interesovanjaRetVal.getBody();
    }
}
