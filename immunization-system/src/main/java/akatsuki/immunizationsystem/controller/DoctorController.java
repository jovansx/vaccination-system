package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doktori")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/basic-info/{dockerId}")
    public String getBasicDocktorInfo(@PathVariable String dockerId) {
        return doctorService.getBasicDocktorInfo(dockerId);
    }

}
