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

    @GetMapping("/by-patient-id/{patientId}")
    public String getSaglasnostByPatientId(@PathVariable String patientId) {
        return saglasnostZaImunizacijuService.getSaglasnostByPatientId(patientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createSaglasnostZaImunizaciju(@RequestBody String saglasnostXml) {
        saglasnostZaImunizacijuService.createSaglasnostZaImunizaciju(saglasnostXml);
    }

    @GetMapping("/pdf/{idBroj}")
    public void getSaglasnostPdf(@PathVariable String idBroj) {
        saglasnostZaImunizacijuService.generatePdf(idBroj);
    }

    @PutMapping
    public void updateSaglasnostZaImunizaciju(@RequestBody String saglasnostXml) {
        saglasnostZaImunizacijuService.updateSaglasnostZaImunizaciju(saglasnostXml);
    }

    @DeleteMapping("/{idBrojIndex}")
    public void deleteSaglasnostZaImunizaciju(@PathVariable String idBrojIndex) {
        saglasnostZaImunizacijuService.deleteSaglasnostZaImunizaciju(idBrojIndex);
    }
}
