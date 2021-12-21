package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.SaglasnostZaImunizacijuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/saglasnosti")
@RequiredArgsConstructor
public class SaglasnostZaImunizacijuController {
    private final SaglasnostZaImunizacijuService saglasnostZaImunizacijuService;

    @GetMapping("/{id}")
    public String getSaglasnostZaImunizaciju(@PathVariable UUID id) {
        return saglasnostZaImunizacijuService.getSaglasnostZaImunizaciju(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createSaglasnostZaImunizaciju(@RequestBody String saglasnostXml) {
        return saglasnostZaImunizacijuService.createSaglasnostZaImunizaciju(saglasnostXml);
    }
}
