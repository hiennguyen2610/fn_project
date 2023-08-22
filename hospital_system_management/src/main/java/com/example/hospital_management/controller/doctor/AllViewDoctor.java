package com.example.hospital_management.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class AllViewDoctor {

    @GetMapping("/appointment")
    public String appointment(){
        return "/doctor/appointment";
    }

    @GetMapping("/patients")
    public String patients(){
        return "/doctor/patients";
    }
}
