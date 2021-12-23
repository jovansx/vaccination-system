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
    public String createInteresovanje(@RequestBody String interesovanjeXml) {
        return interesovanjeService.createInteresovanje(interesovanjeXml);
    }
}
