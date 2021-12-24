package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.PacijentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pacijenti")
@RequiredArgsConstructor
public class PacijentController {

    private final PacijentService pacijentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createDigitalniSertifikat(@RequestBody String pacijentXml) {
        return pacijentService.createPacijenta(pacijentXml);
    }

}
