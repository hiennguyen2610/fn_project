package com.example.hospital_management.controller.doctor;

import com.example.hospital_management.exception.ActivatedAccountException;
import com.example.hospital_management.model.request.DoctorRequest;
import com.example.hospital_management.service.DoctorService;
import com.example.hospital_management.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/doctors")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DoctorController {



}
