package com.example.hospital_management.api;

import com.example.hospital_management.entity.Diagnosis;
import com.example.hospital_management.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diagnosis")
public class DiagnosisResource {

    @Autowired
    private DiagnosisService diagnosisService;

    @GetMapping("/public/findByAppointment")
    public Diagnosis findByAppointment(@RequestParam("idApp") Long idApp){
        return diagnosisService.findByAppointment(idApp);
    }

    @GetMapping("/public/findByPatient")
    public Diagnosis findByPatient(@RequestParam("idPatient") Long idPatient){
        return diagnosisService.findByPatient(idPatient);
    }

    @PostMapping("/public/save")
    public Diagnosis save(@RequestBody Diagnosis diagnosis){
        return diagnosisService.save(diagnosis);
    }
}
