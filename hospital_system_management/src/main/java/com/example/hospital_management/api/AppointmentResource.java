package com.example.hospital_management.api;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.User;
import com.example.hospital_management.model.request.AppointmentRequest;
import com.example.hospital_management.repository.AppointmentRepository;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.service.AppointmentService;
import com.example.hospital_management.service.UserService;
import com.example.hospital_management.statics.AppointmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentResource {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/public/create")
    public ResponseEntity<?> create(@RequestBody AppointmentRequest appointmentRequest){
        Appointment result = appointmentService.create(appointmentRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/doctor/create")
    public ResponseEntity<?> createByDoctor(@RequestBody AppointmentRequest appointmentRequest, HttpServletRequest request){
        Appointment result = appointmentService.createByDoctor(appointmentRequest, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/public/appointmentByDoctor")
    public ResponseEntity<?> appointmentByDoctor(@RequestParam Long doctorId, @RequestParam Date date){
        List<Appointment> result = appointmentService.findByDoctor(doctorId, date);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/public/myAppointment")
    public ResponseEntity<?> myAppointment(@RequestParam String param){
        List<Appointment> result = appointmentRepository.myAppointment(param);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> allApointment(@RequestParam(value = "start", required = false) Date start,
                                           @RequestParam(value = "end", required = false) Date end,
                                           @RequestParam(value = "param", required = false) String param,
                                           Pageable pageable){
        Page<Appointment> result = appointmentService.allApointment(start,end,param,pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/employee/all")
    public ResponseEntity<?> allApointmentByEmployee(@RequestParam(value = "start", required = false) Date start,
                                           @RequestParam(value = "end", required = false) Date end,
                                           @RequestParam(value = "param", required = false) String param,
                                           Pageable pageable){
        Page<Appointment> result = appointmentService.allApointmentByEmp(start,end,param,pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/doctor/all-appointment")
    public ResponseEntity<?> allApointmentByDoctor(@RequestParam(value = "start", required = false) Date start,
                                                   @RequestParam(value = "end", required = false) Date end,
                                                   @RequestParam(value = "param", required = false) String param,
                                                   Pageable pageable, HttpServletRequest request){
        Page<Appointment> result = appointmentService.allApointmentByDoctor(start,end,param,pageable, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/doctor/my-appointment")
    public ResponseEntity<?> allApointmentByDoctor(@RequestParam("date") Date date,HttpServletRequest request){
        List<Appointment> result = appointmentService.myAppointment(date, request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/admin/all-status")
    public ResponseEntity<?> getAllApt(){
        List<AppointmentStatus> list = appointmentService.getAllApt();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/doctor/all-status")
    public ResponseEntity<?> getAllAptByDoctor(){
        List<AppointmentStatus> list = appointmentService.getAllAptByDoctor();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @PostMapping("/admin/accept")
    public void accept(@RequestParam(value = "id") Long id, @RequestParam("status") String status){
        appointmentService.accept(id, status);
    }


    @PostMapping("/public/cancel")
    public void cancel(@RequestParam(value = "id") Long id){
        appointmentService.cancel(id);
    }

    @PostMapping("/public/changeDoctor")
    public void changeDoctor(@RequestParam("idapp") Long idapp, @RequestParam("iddoctor") Long iddoctor){
        appointmentService.changeDoctor(idapp, iddoctor);
    }

    @PostMapping("/public/confirm-pay")
    public void confirmPay(@RequestParam("idapp") Long idapp){
        appointmentService.confirmPay(idapp);
    }
}
