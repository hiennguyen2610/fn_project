package com.example.hospital_management.api;

import com.example.hospital_management.entity.DiagnosisDetail;
import com.example.hospital_management.entity.Service;
import com.example.hospital_management.model.request.DiagnosisDetailRequest;
import com.example.hospital_management.service.DiagnosisDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diagnosisDetail")
public class DiagnosisDetailResource {

    @Autowired
    private DiagnosisDetailService diagnosisDetailService;

    @PostMapping("/doctor/create")
    public ResponseEntity<?> save(@RequestParam Long idAppointment, @RequestBody List<String> listIdService){
        diagnosisDetailService.addServiceToAppointment(idAppointment, listIdService);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping("/public/find-by-appointment")
    public List<DiagnosisDetail> findByApp(@RequestParam("idApp") Long idApp){
        return diagnosisDetailService.findByApp(idApp);
    }

    @GetMapping("/public/find-by-patient")
    public List<DiagnosisDetail> findByPatient(@RequestParam("idPatient") Long idPatient){
        return diagnosisDetailService.findByPatient(idPatient);
    }

    @PostMapping("/doctor/update")
    public void update(@RequestBody List<DiagnosisDetailRequest> list){
        diagnosisDetailService.update(list);
    }

}
