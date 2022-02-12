package akatsuki.officialsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

@RestController
@RequestMapping("/api/document-proxy")
@RequiredArgsConstructor
public class DokumentProxyController {

    private final RestTemplate template;

    @GetMapping(value = "/digitalni-sertifikati/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getSertifikatPdf(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/digitalni-sertifikati/pdf/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/digitalni-sertifikati/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getSertifikatXhtml(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/digitalni-sertifikati/xhtml/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.TEXT_HTML)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/interesovanja/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getInteresovanjePdf(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/interesovanja/pdf/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/interesovanja/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getInteresovanjeXhtml(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/interesovanja/xhtml/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.TEXT_HTML)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/potvrde/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPotvrdaPdf(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/potvrde/pdf/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/potvrde/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getPotvrdaXhtml(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/potvrde/xhtml/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.TEXT_HTML)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/zahtevi/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getZahtevPdf(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/zahtevi/pdf/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/zahtevi/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getZahtevXhtml(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/zahtevi/xhtml/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.TEXT_HTML)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/saglasnosti/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getSaglasnostPdf(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/saglasnosti/pdf/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(targetStream));
    }

    @GetMapping(value = "/saglasnosti/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getSaglasnostXhtml(@PathVariable String idBroj) {
        ResponseEntity<byte[]> response = template.getForEntity("http://localhost:8081/api/saglasnosti/xhtml/" + idBroj, byte[].class);
        InputStream targetStream = new ByteArrayInputStream(Objects.requireNonNull(response.getBody()));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.TEXT_HTML)
                .body(new InputStreamResource(targetStream));
    }
}
