package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.service.DigitalniSertifikatService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/digitalni-sertifikati")
@RequiredArgsConstructor
public class DigitalniSertifikatController {

    private final DigitalniSertifikatService digitalniSertifikatService;

    @GetMapping("/{idBroj}")
    public String getDigitalniSertifikat(@PathVariable String idBroj) {
        return digitalniSertifikatService.getDigitalniSertifikat(idBroj);
    }

    @GetMapping("/pretrazi/{searchInput}")
    public String getDigitalneSertifikateBasicSearch(@PathVariable String searchInput) {
        return digitalniSertifikatService.getSertifikatiBySearchInput(searchInput);
    }

    @GetMapping("/napredno-pretrazi")
    public String getDigitalniSertifikateAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                     @RequestParam(required = false) String id_broj,  @RequestParam(required = false) String pol) {
        return digitalniSertifikatService.getDigitalniSertifikateAdvenced(ime, prezime, id_broj, pol);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDigitalniSertifikat(@RequestBody String digitalniSertifikatXml) {
        return digitalniSertifikatService.createDigitalniSertifikat(digitalniSertifikatXml);
    }

    @GetMapping("/{periodOd}/{periodDo}")
    public int getResourcesCount(@PathVariable String periodOd, @PathVariable String periodDo) {
        return digitalniSertifikatService.getResourcesCountInPeriod(periodOd, periodDo);
    }

    @GetMapping(value = "/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getSertifikatPdf(@PathVariable String idBroj) {
        ByteArrayInputStream stream = digitalniSertifikatService.generatePdf(idBroj);
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

    @GetMapping(value = "/metadata/json/{idBroj}")
    public MetadataDTO getMetadataJSON(@PathVariable String idBroj) {
        return digitalniSertifikatService.getMetadataJSON(idBroj);
    }

    @GetMapping(value = "/metadata/rdf/{idBroj}")
    public String getMetadataRDF(@PathVariable String idBroj) {
        return digitalniSertifikatService.getMetadataRDF(idBroj);
    }

    @GetMapping(value = "/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getSertifikatXhtml(@PathVariable String idBroj) {
        ByteArrayInputStream stream = digitalniSertifikatService.generateXhtml(idBroj);
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
}
