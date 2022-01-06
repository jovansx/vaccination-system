package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.DigitalniSertifikatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/digitalni-sertifikati")
@RequiredArgsConstructor
public class DigitalniSertifikatController {

    private final DigitalniSertifikatService digitalniSertifikatService;

    @GetMapping("/{idBroj}")
    public String getDigitalniSertifikat(@PathVariable String idBroj) {
        return digitalniSertifikatService.getDigitalniSertifikat(idBroj);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDigitalniSertifikat(@RequestBody String digitalniSertifikatXml) {
        return digitalniSertifikatService.createDigitalniSertifikat(digitalniSertifikatXml);
    }

    @GetMapping("/{periodOd}/{periodDo}")
    public int getResourcesCount(@PathVariable String periodOd, @PathVariable String periodDo) {
        return digitalniSertifikatService.getResourcesCountInPeriod(periodOd, periodDo);
    }
}
