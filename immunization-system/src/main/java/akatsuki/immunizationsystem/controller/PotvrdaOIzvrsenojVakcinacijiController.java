package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.PotvrdaOIzvrsenojVakcinacijiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/potvrde")
@RequiredArgsConstructor
public class PotvrdaOIzvrsenojVakcinacijiController {

    private final PotvrdaOIzvrsenojVakcinacijiService potvrdaOIzvrsenojVakcinacijiService;

    @GetMapping("/{jmbgDoza}")
    public String getPotvrdaOIzvrsenojVakcinaciji(@PathVariable String jmbgDoza) {
        return potvrdaOIzvrsenojVakcinacijiService.getPotvrdaOIzvrsenojVakcinaciji(jmbgDoza);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createPotvrdaOIzvrsenojVakcinaciji(@RequestBody String potvrdaOIzvrsenojVakcinacijiXml) {
        return potvrdaOIzvrsenojVakcinacijiService.createPotvrdaOIzvrsenojVakcinaciji(potvrdaOIzvrsenojVakcinacijiXml);
    }
}
