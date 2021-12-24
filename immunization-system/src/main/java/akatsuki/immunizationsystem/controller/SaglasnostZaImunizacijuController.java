package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.SaglasnostZaImunizacijuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saglasnosti")
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuController {
    private final SaglasnostZaImunizacijuService saglasnostZaImunizacijuService;

    @GetMapping("/{idBrojIndex}")
    public String getSaglasnostZaImunizaciju(@PathVariable String idBrojIndex) {
        return saglasnostZaImunizacijuService.getSaglasnostZaImunizaciju(idBrojIndex);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createSaglasnostZaImunizaciju(@RequestBody String saglasnostXml) {
        return saglasnostZaImunizacijuService.createSaglasnostZaImunizaciju(saglasnostXml);
    }
}
