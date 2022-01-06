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

    @GetMapping("/{idBrojDoza}")
    public String getPotvrdaOIzvrsenojVakcinaciji(@PathVariable String idBrojDoza) {
        return potvrdaOIzvrsenojVakcinacijiService.getPotvrdaOIzvrsenojVakcinaciji(idBrojDoza);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createPotvrdaOIzvrsenojVakcinaciji(@RequestBody String potvrdaOIzvrsenojVakcinacijiXml) {
        return potvrdaOIzvrsenojVakcinacijiService.createPotvrdaOIzvrsenojVakcinaciji(potvrdaOIzvrsenojVakcinacijiXml);
    }

    @GetMapping("/doze/{periodOd}/{periodDo}")
    public String getResourcesCountByDoze(@PathVariable String periodOd, @PathVariable String periodDo) {
        return potvrdaOIzvrsenojVakcinacijiService.getResourcesCountByDoze(periodOd, periodDo);
    }

    @GetMapping("/proizvodjaci/{periodOd}/{periodDo}")
    public String getResourcesCountByProizvodjaci(@PathVariable String periodOd, @PathVariable String periodDo) {
        return potvrdaOIzvrsenojVakcinacijiService.getResourcesCountByProizvodjaci(periodOd, periodDo);
    }
}
