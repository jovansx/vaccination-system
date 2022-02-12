package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.service.PotvrdaOIzvrsenojVakcinacijiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/potvrde")
@RequiredArgsConstructor
public class PotvrdaOIzvrsenojVakcinacijiController {

    private final PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService;

    @GetMapping("/{idBrojDoza}")
    public String getPotvrdaOIzvrsenojVakcinaciji(@PathVariable String idBrojDoza) {
        return potvrdaOIzvrsenojVakcinacijiService.getPotvrdaOIzvrsenojVakcinaciji(idBrojDoza);
    }

    @GetMapping("/serie-of-first-vacine/{idBrojDoza}")
    public String getSerijuPrveVakcine(@PathVariable String idBrojDoza) {
        return potvrdaOIzvrsenojVakcinacijiService.getSerijuPrveVakcine(idBrojDoza);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPotvrdaOIzvrsenojVakcinaciji(@RequestBody String potvrdaOIzvrsenojVakcinacijiXml) {
        potvrdaOIzvrsenojVakcinacijiService.createPotvrdaOIzvrsenojVakcinaciji(potvrdaOIzvrsenojVakcinacijiXml);
    }

    @GetMapping("/doze/{periodOd}/{periodDo}")
    public String getResourcesCountByDoze(@PathVariable String periodOd, @PathVariable String periodDo) {
        return potvrdaOIzvrsenojVakcinacijiService.getResourcesCountByDoze(periodOd, periodDo);
    }

    @GetMapping("/proizvodjaci/{periodOd}/{periodDo}")
    public String getResourcesCountByProizvodjaci(@PathVariable String periodOd, @PathVariable String periodDo) {
        return potvrdaOIzvrsenojVakcinacijiService.getResourcesCountByProizvodjaci(periodOd, periodDo);
    }

    @GetMapping(value = "/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getPotvrdaPdf(@PathVariable String idBroj) {
        ByteArrayInputStream stream = potvrdaOIzvrsenojVakcinacijiService.generatePdf(idBroj);
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

    @GetMapping(value = "/metadata/json/{idBrojDoza}")
    public MetadataDTO getMetadataJSON(@PathVariable String idBrojDoza) {
        return potvrdaOIzvrsenojVakcinacijiService.getMetadataJSON(idBrojDoza);
    }

    @GetMapping(value = "/metadata/rdf/{idBroj}")
    public String getMetadataRDF(@PathVariable String idBroj) {
        return potvrdaOIzvrsenojVakcinacijiService.getMetadataRDF(idBroj);
    }

    @GetMapping(value = "/xhtml/{idBroj}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<InputStreamResource> getPotvrdaXhtml(@PathVariable String idBroj) {
        ByteArrayInputStream stream = potvrdaOIzvrsenojVakcinacijiService.generateXhtml(idBroj);
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

    @GetMapping("/druge")
    public String getDrugePotvrde() {
        return potvrdaOIzvrsenojVakcinacijiService.getDrugePotvrde();
    }

}
