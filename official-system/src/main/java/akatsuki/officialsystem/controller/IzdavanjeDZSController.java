package akatsuki.officialsystem.controller;

import akatsuki.officialsystem.service.IzdavanjeDZSService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/izdavanje-dzs")
@RequiredArgsConstructor
public class IzdavanjeDZSController {
    private final IzdavanjeDZSService izdavanjeDZSService;

    @GetMapping("/zahtevi")
    public String getAllNeodobreniZahteviAndPotvrde() {
        return izdavanjeDZSService.getAllNeodobreniZahteviAndPotvrde();
    }
}
