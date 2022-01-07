package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.SaglasnostZaImunizacijuService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/saglasnosti")
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuController {
    private final SaglasnostZaImunizacijuService saglasnostZaImunizacijuService;

    @GetMapping("/{idBrojIndex}")
    public String getSaglasnostZaImunizaciju(@PathVariable String idBrojIndex) {
        return saglasnostZaImunizacijuService.getSaglasnostZaImunizaciju(idBrojIndex);
    }

    @GetMapping("/by-patient-id/{patientId}")
    public String getSaglasnostByPatientId(@PathVariable String patientId) {
        return saglasnostZaImunizacijuService.getSaglasnostByPatientId(patientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSaglasnostZaImunizaciju(@RequestBody String saglasnostXml) {
        saglasnostZaImunizacijuService.createSaglasnostZaImunizaciju(saglasnostXml);
    }

    @GetMapping(value = "/pdf/{idBrojIndex}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getSaglasnostPdf(@PathVariable String idBrojIndex) {
        ByteArrayInputStream stream = saglasnostZaImunizacijuService.generatePdf(idBrojIndex);
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
    public ResponseEntity<InputStreamResource> getSaglasnostXhtml(@PathVariable String idBroj) {
        ByteArrayInputStream stream = saglasnostZaImunizacijuService.generateXhtml(idBroj);
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

    @PutMapping
    public void updateSaglasnostZaImunizaciju(@RequestBody String saglasnostXml) {
        saglasnostZaImunizacijuService.updateSaglasnostZaImunizaciju(saglasnostXml);
    }

    @DeleteMapping("/{idBrojIndex}")
    public void deleteSaglasnostZaImunizaciju(@PathVariable String idBrojIndex) {
        saglasnostZaImunizacijuService.deleteSaglasnostZaImunizaciju(idBrojIndex);
    }
}
