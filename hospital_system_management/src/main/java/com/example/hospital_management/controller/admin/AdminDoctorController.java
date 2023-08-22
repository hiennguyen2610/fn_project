package com.example.hospital_management.controller.admin;

import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.model.request.*;
import com.example.hospital_management.model.response.CommonResponse;
import com.example.hospital_management.model.response.DoctorResponse;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.service.DoctorService;
import com.example.hospital_management.service.SpecialityService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
@RequestMapping()
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminDoctorController {

    DoctorService doctorService;
    SpecialityService specialityService;

    @Autowired
    private DoctorRepository doctorRepository;

//    @GetMapping("/admin/doctors")
//    public String doctorList(Model model) {
//        List<DoctorResponse> doctorResponsePage = doctorService.getAllDoctorResponse();
//        List<Speciality> specialityList = specialityService.getAllSpecialities();
//        model.addAttribute("getAllDoctor", doctorResponsePage);
//        model.addAttribute("listAllSpecialities", specialityList);
//
//        return "admin/doctor/doctors";
//    }

    @GetMapping("/admin/doctors")
    public String searchDoctor(Model model, DoctorSearchRequest request) {
        CommonResponse<?> commonResponse = doctorService.searchDoctor(request);
        model.addAttribute("pageDoctorInfo", commonResponse);
        model.addAttribute("listAllSpecialities", specialityService.findAll());
        model.addAttribute("currentPage", request.getPageIndex());
        model.addAttribute("pageSize", request.getPageSize());
        return "admin/doctor/doctors";
    }

    @GetMapping("/admin/add-doctor")
    public String addDoctor(Model model) {
        model.addAttribute("listAllSpecialities", specialityService.getAllSpecialities());
        return "admin/doctor/add-doctor";
    }

    @GetMapping("/api/v1/admin/doctor/{id}")
    public ResponseEntity<?> getDoctor(@PathVariable("id") Long id) {
        return ResponseEntity.ok(doctorService.findById(id));
    }

    @PutMapping("/api/v1/admin/doctor/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable("id") Long id, @RequestBody @Valid UpdateDoctorRequest registrationRequest) {
        doctorService.updateDoctor(id, registrationRequest);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/api/v1/admin/doctor/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }


}
