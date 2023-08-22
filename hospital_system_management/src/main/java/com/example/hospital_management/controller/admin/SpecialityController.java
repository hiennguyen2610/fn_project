package com.example.hospital_management.controller.admin;

import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.model.request.*;
import com.example.hospital_management.model.response.ErrorResponse;
import com.example.hospital_management.service.SpecialityService;
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

public class SpecialityController {

    SpecialityService specialityService;

    @GetMapping("/admin/departments")
    public String department(@RequestParam(required = false, defaultValue = "1") Integer page,
                             @RequestParam(required = false, defaultValue = "6") Integer pageSize, Model model) {
        Page<Speciality> pageInfo = specialityService.getAllSpecialityPage(page, pageSize);
        List<Speciality> specialityList = specialityService.getAllSpecialities();
        model.addAttribute("listAllSpecialities", specialityList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentPage", page);
        return "admin/speciality/departments";
    }

    @GetMapping("/admin/add-department")
    public String addDepartment() {
        return "admin/speciality/add-department";
    }

    @PostMapping("/api/v1/admin/specialities")
    public ResponseEntity<?> createSpeciality(@RequestBody SpecialityRequest specialityRequest) {
        try {
            Speciality speciality = specialityService.create(specialityRequest.getName(), specialityRequest.getDescription());
            return ResponseEntity.ok(speciality);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/v1/admin/specialities/{id}")
    public ResponseEntity<?> getSpeciality(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(specialityService.getSpeciality(id));
    }

    @PutMapping("/api/v1/admin/specialities/{id}")
    public ResponseEntity<?> updateSpeciality(@PathVariable("id") Integer id, @RequestBody SpecialityRequest specialityRequest) {
        try {
            Speciality speciality = specialityService.update(id, specialityRequest.getName(), specialityRequest.getDescription());
            return ResponseEntity.ok(speciality);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/v1/admin/specialities/{id}")
    public ResponseEntity<?> deleteSpeciality(@PathVariable("id") Long id) {
        specialityService.deleteSpeciality(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}

