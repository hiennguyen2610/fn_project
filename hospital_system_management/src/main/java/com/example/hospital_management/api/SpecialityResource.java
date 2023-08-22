package com.example.hospital_management.api;

import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.repository.SpecialityRepository;
import com.example.hospital_management.service.SpecialityService;
import com.example.hospital_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/speciality")
public class SpecialityResource {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    public SpecialityService specialityService;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/find-all")
    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(specialityRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/public/all")
    public ResponseEntity<?> getAllSpeciality(){
        List<Speciality> result = specialityService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/public/findByDoctor")
    public ResponseEntity<?> findByDoctor(HttpServletRequest request){
        List<Speciality> result = specialityRepository.findByDoctor(userService.getUserLogged(request).getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
