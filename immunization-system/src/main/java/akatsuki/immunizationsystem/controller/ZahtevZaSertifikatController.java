package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.ZahtevZaSertifikatService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/zahtevi")
@RequiredArgsConstructor
public class ZahtevZaSertifikatController {
    private final ZahtevZaSertifikatService zahtevZaSertifikatService;

    @GetMapping("/{idBroj}")
    public String getZahtevZaSertifikat(@PathVariable String idBroj) {
        return zahtevZaSertifikatService.getZahtevZaSertifikat(idBroj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createZahtevZaSertifikat(@RequestBody String zahtevXml) {
        zahtevZaSertifikatService.createZahtevZaSertifikat(zahtevXml);
    }

    @GetMapping(value = "/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getZahtevPdf(@PathVariable String idBroj) {
        ByteArrayInputStream stream = zahtevZaSertifikatService.generatePdf(idBroj);
        if (stream == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }

    @GetMapping(value = "/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getZahtevXhtml(@PathVariable String idBroj) {
        ByteArrayInputStream stream = zahtevZaSertifikatService.generateXhtml(idBroj);
        if (stream == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.html");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.TEXT_HTML)
                .body(new InputStreamResource(stream));
    }

    @GetMapping("/{periodOd}/{periodDo}")
    public int getResourcesCount(@PathVariable String periodOd, @PathVariable String periodDo) {
        return zahtevZaSertifikatService.getResourcesCountInPeriod(periodOd, periodDo);
    }

}
