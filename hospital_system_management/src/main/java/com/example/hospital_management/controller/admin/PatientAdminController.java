package com.example.hospital_management.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class PatientAdminController {

    @GetMapping("/patients")
    public String patients(){
        return "/admin/patients/patients";
    }
}
