package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.InteresovanjeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interesovanja")
@RequiredArgsConstructor
public class InteresovanjeController {

    private final InteresovanjeService interesovanjeService;

    @GetMapping("/{jmbg}")
    public String getInteresovanje(@PathVariable String jmbg) {
        return interesovanjeService.getInteresovanje(jmbg);
    }

    @PostMapping
    public String createInteresovanje(@RequestBody String interesovanjeXml) {
        return interesovanjeService.createInteresovanje(interesovanjeXml);
    }
}
