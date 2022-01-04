package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.dtos.*;
import akatsuki.immunizationsystem.model.documents.SaglasnostZaImunizaciju;
import akatsuki.immunizationsystem.model.users.Pacijent;
import akatsuki.immunizationsystem.service.PacijentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacijenti")
@RequiredArgsConstructor
public class PacijentController {

    private final PacijentService pacijentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPacijenta(@RequestBody String pacijentXml) {
        pacijentService.createPacijenta(pacijentXml);
    }

    @GetMapping(value = "/trenutna-forma/{idBroj}", produces = MediaType.APPLICATION_XML_VALUE)
    public AktivnaFormaDTO getTrenutnaForma(@PathVariable String idBroj) {
        String dokument = pacijentService.getTrenutnaForma(idBroj);
        return new AktivnaFormaDTO(dokument);
    }

    @GetMapping(value = "/interesovanje-detalji/{idBroj}", produces = MediaType.APPLICATION_XML_VALUE)
    public PacijentInteresovanjeDTO getDetailsForInteresovanje(@PathVariable String idBroj) {
        Pacijent pacijent = pacijentService.getPacijent(idBroj);
        return new PacijentInteresovanjeDTO(pacijent);
    }

    @GetMapping(value = "/saglasnost-detalji/{idBroj}", produces = MediaType.APPLICATION_XML_VALUE)
    public PacijentSaglasnostDTO getDetailsForSaglasnost(@PathVariable String idBroj) {
        Pacijent pacijent = pacijentService.getPacijent(idBroj);
        return new PacijentSaglasnostDTO(pacijent);
    }

    @GetMapping(value = "/zahtev-detalji/{idBroj}", produces = MediaType.APPLICATION_XML_VALUE)
    public PacijentZahtevDTO getDetailsForZahtev(@PathVariable String idBroj) {
        Pacijent pacijent = pacijentService.getPacijent(idBroj);
        return new PacijentZahtevDTO(pacijent);
    }

    @GetMapping(value = "/moji-dokumenti/{idBroj}", produces = MediaType.APPLICATION_XML_VALUE)
    public PacijentDokumentiDTO getPatientDocuments(@PathVariable String idBroj) {
        PacijentDokumentiDTO dto = new PacijentDokumentiDTO();
        List<DokumentIdDTO> dokumenti = pacijentService.getPatientDocuments(idBroj);
        dto.setDokumenti(dokumenti);
        return dto;
    }
}
