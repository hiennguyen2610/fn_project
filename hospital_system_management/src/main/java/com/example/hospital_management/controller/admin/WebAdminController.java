package com.example.hospital_management.controller.admin;

import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.model.response.DoctorResponse;
import com.example.hospital_management.service.DoctorService;
import com.example.hospital_management.service.SpecialityService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class WebAdminController {

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminHome() {
        return "admin/index";
    }
}
