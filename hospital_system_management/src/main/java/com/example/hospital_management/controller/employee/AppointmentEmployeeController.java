package com.example.hospital_management.controller.employee;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppointmentEmployeeController {

    @GetMapping("/employee/appointment")
    public String getAllAppointment() {
        return "/employee/appointments";
    }
}
