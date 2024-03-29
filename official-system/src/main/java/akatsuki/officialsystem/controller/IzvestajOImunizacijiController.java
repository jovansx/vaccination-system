package akatsuki.officialsystem.controller;

import akatsuki.officialsystem.service.IzvestajOImunizacijiService;
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

    @GetMapping
    public String getAll() {
        return izvestajOImunizacijiService.getAll();
    }

    @GetMapping("/{periodOd}/{periodDo}")
    public String getOneByPeriod(@PathVariable String periodOd, @PathVariable String periodDo) {
        return izvestajOImunizacijiService.getIzvestajOImunizacijiByPeriod(periodOd, periodDo);
    }
}
