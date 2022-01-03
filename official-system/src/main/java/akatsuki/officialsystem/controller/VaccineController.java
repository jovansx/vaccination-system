package akatsuki.officialsystem.controller;

import akatsuki.officialsystem.model.vaccine.VaccineType;
import akatsuki.officialsystem.service.VaccineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/vakcine")
@RequiredArgsConstructor
public class VaccineController {

    private final VaccineService vaccineService;

    @PutMapping("/{vaccineType}/{serialNumber}")
    public void decreaseAmount(@PathVariable VaccineType vaccineType, @PathVariable Long serialNumber) {
        vaccineService.decreaseAmount(vaccineType, serialNumber);
    }

    @PutMapping
    public void updateAmount(@RequestBody String xmlUpdateVaccineAmount) {
        vaccineService.updateAmount(xmlUpdateVaccineAmount);
    }

    @GetMapping
    public String getAll() {
        return vaccineService.getAll();
    }
}
