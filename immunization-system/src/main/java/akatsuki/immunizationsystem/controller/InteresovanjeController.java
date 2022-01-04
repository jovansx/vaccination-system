package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.InteresovanjeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interesovanja")
@RequiredArgsConstructor
public class InteresovanjeController {

    private final InteresovanjeService interesovanjeService;

    @GetMapping("/{idBroj}")
    public String getInteresovanje(@PathVariable String idBroj) {
        return interesovanjeService.getInteresovanje(idBroj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createInteresovanje(@RequestBody String interesovanjeXml) {
        interesovanjeService.createInteresovanje(interesovanjeXml);
    }

    @GetMapping("/pdf/{idBroj}")
    public void getInteresovanjePdf(@PathVariable String idBroj) {
        interesovanjeService.generatePdf(idBroj);
    }
}
