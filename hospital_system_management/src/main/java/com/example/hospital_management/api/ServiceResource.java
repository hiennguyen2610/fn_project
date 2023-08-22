package com.example.hospital_management.api;

import com.example.hospital_management.entity.Service;
import com.example.hospital_management.repository.ServiceRepository;
import com.example.hospital_management.service.SvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
public class ServiceResource {

    @Autowired
    private SvService svService;

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping("/admin/create")
    public ResponseEntity<?> save(@RequestBody Service service){
        Service result = svService.createService(service.getName(), service.getPrice());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PostMapping("/admin/update")
    public ResponseEntity<?> update(@RequestBody Service service){
        Service result = svService.updateService(service.getId(),service.getName(), service.getPrice());
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        svService.deleteService(id);
        return new ResponseEntity<>("sucess", HttpStatus.ACCEPTED);
    }

    @GetMapping("/public/all")
    public Page<Service> findAll(Pageable pageable){
        return serviceRepository.findAll(pageable);
    }

    @GetMapping("/public/all-service")
    public List<Service> findAllList(){
        return serviceRepository.findAll();
    }
}
