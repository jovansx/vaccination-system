package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.ZahtevZaSertifikatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zahtevi")
@RequiredArgsConstructor
public class ZahtevZaSertifikatController {
    private final ZahtevZaSertifikatService zahtevZaSertifikatService;

    @GetMapping("/{idBrojIndex}")
    public String getZahtevZaSertifikat(@PathVariable String idBrojIndex) {
        return zahtevZaSertifikatService.getZahtevZaSertifikat(idBrojIndex);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createZahtevZaSertifikat(@RequestBody String zahtevXml) {
        return zahtevZaSertifikatService.createZahtevZaSertifikat(zahtevXml);
    }
}
