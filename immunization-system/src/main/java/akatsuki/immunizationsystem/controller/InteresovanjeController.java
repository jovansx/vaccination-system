package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.InteresovanjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interesovanja")
public class InteresovanjeController {

    @Autowired
    private InteresovanjeService interesovanjeService;

    @GetMapping("/{jmbg}")
    public String getInteresovanje(@PathVariable String jmbg) {
        return interesovanjeService.getInteresovanje(jmbg);
    }

    @PostMapping
    public String createInteresovanje(@RequestBody String interesovanjeXml) {
        return interesovanjeService.createInteresovanje(interesovanjeXml);
    }
}
