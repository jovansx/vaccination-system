package akatsuki.reportsystem.controller;

import akatsuki.reportsystem.service.IzvestajOImunizacijiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/izvestaji")
@RequiredArgsConstructor
public class IzvestajOImunizacijiController {

    private final IzvestajOImunizacijiService izvestajOImunizacijiService;

    @GetMapping("/{periodOdDo}")
    public String get(@PathVariable String periodOdDo) {
        return izvestajOImunizacijiService.getIzvestajOImunizaciji(periodOdDo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createIzvestajOImunizaciji(@RequestBody String izvestajOImunizacijiXml) {
        return izvestajOImunizacijiService.createIzvestajOImunizaciji(izvestajOImunizacijiXml);
    }
}
