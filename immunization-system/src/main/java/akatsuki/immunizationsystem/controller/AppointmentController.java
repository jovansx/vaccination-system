package akatsuki.immunizationsystem.controller;

import akatsuki.immunizationsystem.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/current")
    public String getCurrentAppointment() {
        return appointmentService.getCurrentAppointment();
    }
}
