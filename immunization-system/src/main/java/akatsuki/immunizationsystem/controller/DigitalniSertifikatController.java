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

    @GetMapping("/{jmbg}")
    public String getDigitalniSertifikat(@PathVariable String jmbg) {
        return digitalniSertifikatService.getDigitalniSertifikat(jmbg);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDigitalniSertifikat(@RequestBody String digitalniSertifikatXml) {
        return digitalniSertifikatService.createDigitalniSertifikat(digitalniSertifikatXml);
    }
}
