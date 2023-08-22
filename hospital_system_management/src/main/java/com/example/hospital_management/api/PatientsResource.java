package com.example.hospital_management.api;

import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patient")
public class PatientsResource {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/public/findAllHasDiagnosi")
    public Page<Patient> findAllHasDiagnosi(@RequestParam("param") String search, Pageable pageable){
        return patientRepository.findAlls("%"+search+"%",pageable);
    }
}
