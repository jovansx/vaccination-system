package akatsuki.officialsystem.controller;

import akatsuki.officialsystem.service.PretragaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pretraga")
@RequiredArgsConstructor
public class PretragaController {

    private final PretragaService pretragaService;

    @GetMapping("/{searchInput}")
    public String getDocumentBasicSearch(@PathVariable String searchInput) {
        return pretragaService.basicSearchDocuments(searchInput);
    }

    @GetMapping
    public String getAllDocuments() {
        return pretragaService.basicSearchDocuments(null);
    }

    @GetMapping("/interesovanje")
    public String getInteresovanjeAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                           @RequestParam(required = false) String id_broj, @RequestParam(required = false) String lokacija) {
        return pretragaService.getInteresovanjeAdvenced(ime, prezime, id_broj, lokacija);
    }

    @GetMapping("/saglasnosti")
    public String getSaglasnostAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                           @RequestParam(required = false) String id_broj, @RequestParam(required = false) String lokacija, @RequestParam(required = false) String pol) {
        return pretragaService.getSaglasnostAdvenced(ime, prezime, id_broj, lokacija, pol);
    }

    @GetMapping("/potvrda")
    public String getPotvrdaAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                        @RequestParam(required = false) String id_broj) {
        return pretragaService.getPotvrdaAdvenced(ime, prezime, id_broj);
    }

    @GetMapping("/sertifikat")
    public String getSertifikatAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                     @RequestParam(required = false) String id_broj, @RequestParam(required = false) String pol) {
        return pretragaService.getSertifikatAdvenced(ime, prezime, id_broj, pol);
    }

    @GetMapping("/zahtev")
    public String getZahtevAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                        @RequestParam(required = false) String id_broj, @RequestParam(required = false) String pol) {
        return pretragaService.getZahtevAdvenced(ime, prezime, id_broj, pol);
    }
}
