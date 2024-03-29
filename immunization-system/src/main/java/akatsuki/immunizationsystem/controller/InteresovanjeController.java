package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.dtos.MetadataDTO;
import akatsuki.immunizationsystem.service.InteresovanjeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/api/interesovanja")
@RequiredArgsConstructor
public class InteresovanjeController {

    private final InteresovanjeService interesovanjeService;

    @GetMapping("/{idBroj}")
    public String getInteresovanje(@PathVariable String idBroj) {
        return interesovanjeService.getInteresovanje(idBroj);
    }

    @GetMapping("/pretrazi/{searchInput}")
    public String getInteresovanjaBasicSearch(@PathVariable String searchInput) {
        return interesovanjeService.getInteresovanjaBySearchInput(searchInput);
    }

    @GetMapping("/napredno-pretrazi")
    public String getInteresovanjaAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                           @RequestParam(required = false) String id_broj, @RequestParam(required = false) String lokacija) {
        return interesovanjeService.getInteresovanjaAdvenced(ime, prezime, id_broj, lokacija);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInteresovanje(@RequestBody String interesovanjeXml) {
        interesovanjeService.createInteresovanje(interesovanjeXml);
    }

    @GetMapping(value = "/pdf/{idBroj}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getInteresovanjePdf(@PathVariable String idBroj) {
        ByteArrayInputStream stream = interesovanjeService.generatePdf(idBroj);
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
    public ResponseEntity<InputStreamResource> getInteresovanjeXhtml(@PathVariable String idBroj) {
        ByteArrayInputStream stream = interesovanjeService.generateXhtml(idBroj);
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

    @GetMapping(value = "/metadata/json/{idBroj}")
    public MetadataDTO getMetadataJSON(@PathVariable String idBroj) {
        return interesovanjeService.getMetadataJSON(idBroj);
    }

    @GetMapping(value = "/metadata/rdf/{idBroj}")
    public String getMetadataRDF(@PathVariable String idBroj) {
        return interesovanjeService.getMetadataRDF(idBroj);
    }

    @GetMapping("/{periodOd}/{periodDo}")
    public int getResourcesCount(@PathVariable String periodOd, @PathVariable String periodDo) {
        return interesovanjeService.getResourcesCountInPeriod(periodOd, periodDo);
    }

}
