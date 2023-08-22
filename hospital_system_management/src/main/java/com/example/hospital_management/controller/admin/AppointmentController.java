package com.example.hospital_management.controller.admin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    @GetMapping("/admin/appointments")
    public String getAllAppointment() {
        return "/admin/appointment/appointments";
    }

    @GetMapping("/admin/edit-appointment")
    public String updateAppointment() {
        return "/admin/appointment/edit-appointment";
    }
}
