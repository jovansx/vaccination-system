package akatsuki.officialsystem.controller;

import akatsuki.officialsystem.service.IzdavanjeDZSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/izdavanje-dzs")
@RequiredArgsConstructor
public class IzdavanjeDZSController {
    private final IzdavanjeDZSService izdavanjeDZSService;

    @GetMapping("/zahtevi")
    public String getAllNeodobreniZahteviAndPotvrde() {
        return izdavanjeDZSService.getAllNeodobreniZahteviAndPotvrde();
    }

    @PostMapping
    public String createDZS(@RequestBody String digitalniSertifikatXml) {
        return izdavanjeDZSService.createDZS(digitalniSertifikatXml);
    }
}
