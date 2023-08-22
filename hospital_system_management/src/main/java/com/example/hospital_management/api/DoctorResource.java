package com.example.hospital_management.api;

import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.model.response.DoctorResponse;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorResource {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/public/all")
    public ResponseEntity<?> getAllSpeciality(@RequestParam Long idSpecialy){
        List<DoctorResponse> result = doctorService.findBySpecialy(idSpecialy);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/public/all-page")
    public ResponseEntity<?> findAll(Pageable pageable){
        return new ResponseEntity<>(doctorRepository.findAllPage(pageable), HttpStatus.OK);
    }

    @GetMapping("/admin/all-page")
    public ResponseEntity<?> findAllByAdmin(@RequestParam("param") String param,
                                            @RequestParam(value = "idspecialy", required = false) Long idspecialy,  Pageable pageable){
        Page<Doctor> page = doctorService.findAllByAdmin(param, idspecialy, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping("/public/findDoctorBySpecialy")
    public List<Doctor> findBySpecialy(@RequestParam("idspecialy") Long idspecialy,@RequestParam("iddoctor") Long iddoctor){
        return doctorRepository.findBySpecialityAndDoctor(idspecialy, iddoctor);
    }
}
