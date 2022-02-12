package akatsuki.officialsystem.controller;

import akatsuki.officialsystem.service.PretragaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pretraga")
@RequiredArgsConstructor
public class PretragaController {

    private final PretragaService pretragaService;

    @GetMapping("/{searchInput}")
    public String getDocumentBasicSearch(@PathVariable String searchInput) {
        return pretragaService.basicSearchDocuments(searchInput);
    }
}
