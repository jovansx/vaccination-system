package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.model.documents.ZahtevZaSertifikat;
import akatsuki.immunizationsystem.service.SaglasnostZaImunizacijuService;
import akatsuki.immunizationsystem.service.ZahtevZaSertifikatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/zahtevi")
@RequiredArgsConstructor
public class ZahtevZaSertifikatController {
    private final ZahtevZaSertifikatService zahtevZaSertifikatService;

    @GetMapping("/{jmbg}/{id}")
    public String getZahtevZaSertifikat(@PathVariable String jmbg, @PathVariable UUID id) {
        return zahtevZaSertifikatService.getZahtevZaSertifikat(jmbg, id);
    }

}
