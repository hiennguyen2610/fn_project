package com.example.hospital_management.controller.admin;

import com.example.hospital_management.entity.Service;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.model.request.*;
import com.example.hospital_management.model.response.ErrorResponse;
import com.example.hospital_management.service.SvService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ServiceController {

    SvService svService;

    @GetMapping("/admin/service")
    public String services() {
        return "admin/service/services";
    }

    @GetMapping("/admin/add-service")
    public String addServices() {
        return "admin/service/add-service";
    }
}

