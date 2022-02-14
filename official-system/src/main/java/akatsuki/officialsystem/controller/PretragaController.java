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

    @GetMapping("/interesovanje")
    public String getInteresovanjeAdvenced(@RequestParam(required = false) String ime, @RequestParam(required = false) String prezime,
                                           @RequestParam(required = false) String id_broj, @RequestParam(required = false) String lokacija) {
        return pretragaService.getInteresovanjeAdvenced(ime, prezime, id_broj, lokacija);
    }
}
