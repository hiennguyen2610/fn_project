package com.example.hospital_management.service;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.entity.User;
import com.example.hospital_management.exception.NotFoundException;
import com.example.hospital_management.model.request.AppointmentRequest;
import com.example.hospital_management.repository.AppointmentRepository;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.repository.PatientRepository;
import com.example.hospital_management.statics.AppointmentStatus;
import com.example.hospital_management.statics.HealthInsuranceType;
import com.example.hospital_management.statics.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment create(AppointmentRequest appointmentRequest){
        Patient patient = new Patient();
        patient.setAddress(appointmentRequest.getAddress());
        patient.setDob(appointmentRequest.getDob());
        patient.setEmail(appointmentRequest.getEmail());
        patient.setSymptom(appointmentRequest.getSymptom());
        patient.setName(appointmentRequest.getName());
        patient.setHealthInsuranceNumber(1);
        patient.setHealthInsuranceType(HealthInsuranceType.TYPE_1);
        patient.setPhone(appointmentRequest.getPhone());
        patient.setDob(appointmentRequest.getDob());
        Patient resultPa = patientRepository.save(patient);


        Appointment appointment = new Appointment();
        appointment.setPaymentStatus(PaymentStatus.UNPAID);
        appointment.setAmount(0);
        appointment.setPaidAmount(0);
        appointment.setAppointmentStatus(AppointmentStatus.APPOINTMENT_CREATED);
        appointment.setDoctor(appointmentRequest.getDoctor());
        appointment.setSpeciality(appointmentRequest.getSpeciality());
        appointment.setSymptom(appointment.getSymptom());
        appointment.setPatient(resultPa);
        appointment.setCreatedDateTime(LocalDateTime.now());
        appointment.setCreatedBy(appointmentRequest.getEmail());
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        Appointment result = appointmentRepository.save(appointment);
        return result;
    }

    public Appointment createByDoctor(AppointmentRequest appointmentRequest, HttpServletRequest request){
        Patient patient = new Patient();
        patient.setAddress(appointmentRequest.getAddress());
        patient.setDob(appointmentRequest.getDob());
        patient.setEmail(appointmentRequest.getEmail());
        patient.setSymptom(appointmentRequest.getSymptom());
        patient.setName(appointmentRequest.getName());
        patient.setHealthInsuranceNumber(1);
        patient.setHealthInsuranceType(HealthInsuranceType.TYPE_1);
        patient.setPhone(appointmentRequest.getPhone());
        patient.setDob(appointmentRequest.getDob());
        Patient resultPa = patientRepository.save(patient);

        Doctor doctor = doctorRepository.findByUser(userService.getUserLogged(request).getId());
        System.out.println("===========> doctor create: "+doctor.getId());
        Appointment appointment = new Appointment();
        appointment.setPaymentStatus(PaymentStatus.UNPAID);
        appointment.setAmount(0);

        appointment.setPaidAmount(0);
        appointment.setAppointmentStatus(AppointmentStatus.ADMIN_APPROVED);
        appointment.setDoctor(doctor);
        appointment.setSpeciality(appointmentRequest.getSpeciality());
        appointment.setSymptom(appointment.getSymptom());
        appointment.setPatient(resultPa);
        appointment.setCreatedDateTime(LocalDateTime.now());
        appointment.setCreatedBy(appointmentRequest.getEmail());
        appointment.setAppointmentDate(appointmentRequest.getAppointmentDate());
        appointment.setAppointmentTime(appointmentRequest.getAppointmentTime());
        Appointment result = appointmentRepository.save(appointment);
        return result;
    }

    public List<Appointment> findByDoctor(Long doctorId, Date date){
        return appointmentRepository.findByDoctor(doctorId, date);
    }

    public Page<Appointment> allApointment(Date start, Date end,String param,Pageable pageable){
        if(start == null || end == null){
            start = Date.valueOf("2000-01-01");
            end = Date.valueOf("2200-01-01");
        }
        if (param == null){
            param = "";
        }
        Page<Appointment> result = appointmentRepository.findByAdmin(start,end,"%"+param+"%",pageable);
        return result;
    }

    public Page<Appointment> allApointmentByEmp(Date start, Date end,String param,Pageable pageable){
        if(start == null || end == null){
            start = Date.valueOf("2000-01-01");
            end = Date.valueOf("2200-01-01");
        }
        if (param == null){
            param = "";
        }
        Page<Appointment> result = appointmentRepository.findByEmp(start,end,"%"+param+"%", AppointmentStatus.CANCELLED,pageable);
        return result;
    }


    public Page<Appointment> allApointmentByDoctor(Date start, Date end,String param,Pageable pageable, HttpServletRequest request){
        if(start == null || end == null){
            start = Date.valueOf("2000-01-01");
            end = Date.valueOf("2200-01-01");
        }
        if (param == null){
            param = "";
        }
        User user = userService.getUserLogged(request);
        System.out.println("============ doctor ===============");
        System.out.println(user.getId());
        Page<Appointment> result = appointmentRepository.findByDoctor(start,end,"%"+param+"%", user.getId(),
                AppointmentStatus.ADMIN_APPROVED, AppointmentStatus.DOCTOR_REJECTED,pageable);
        return result;
    }

    public List<Appointment> myAppointment(Date date,HttpServletRequest request){
        User user = userService.getUserLogged(request);
        System.out.println("============ doctor ===============");
        System.out.println(user.getId());
        List<Appointment> result = appointmentRepository.findByDoctorUser(user.getId(),date);
        return result;
    }

    public List<AppointmentStatus> getAllApt(){
        AppointmentStatus[] result = AppointmentStatus.values();
        List<AppointmentStatus> list = new LinkedList<>(Arrays.asList(result));
        list.removeIf(p -> p.equals(AppointmentStatus.CANCELLED));
        list.removeIf(p -> p.equals(AppointmentStatus.APPOINTMENT_CREATED));
        list.removeIf(p -> p.equals(AppointmentStatus.DOCTOR_REJECTED));
        return list;
    }

    public List<AppointmentStatus> getAllAptByDoctor(){
        AppointmentStatus[] result = AppointmentStatus.values();
        List<AppointmentStatus> list = new LinkedList<>(Arrays.asList(result));
        list.removeIf(p -> p.equals(AppointmentStatus.CANCELLED));
        list.removeIf(p -> p.equals(AppointmentStatus.APPOINTMENT_CREATED));
        list.removeIf(p -> p.equals(AppointmentStatus.ADMIN_APPROVED));
        return list;
    }

    public void accept(Long id, String status){
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        System.out.println("============ status: "+status);
        if (appointment.isPresent()) {
            for (AppointmentStatus s : AppointmentStatus.values()) {
                if (s.getName().equals(status)){
                    System.out.println(s+"-0");
                    appointment.get().setAppointmentStatus(s);
                    appointmentRepository.save(appointment.get());
                    return;
                }
            }
        }
    }

    public void cancel(Long id){
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {
            appointment.get().setAppointmentStatus(AppointmentStatus.CANCELLED);
            appointmentRepository.save(appointment.get());
        }
        else{
            throw new NotFoundException("appointment: "+id+" not found");
        }
    }

    public void changeDoctor(Long idapp, Long iddoctor){
        Appointment appointment = appointmentRepository.findById(idapp).get();
        appointment.setAppointmentStatus(AppointmentStatus.APPOINTMENT_CREATED);
        Doctor doctor = doctorRepository.findById(iddoctor).get();
        appointment.setDoctor(doctor);
        appointmentRepository.save(appointment);
    }

    public void confirmPay(Long idapp){
        Appointment appointment = appointmentRepository.findById(idapp).get();
        if(appointment.getPaymentStatus().name().equals(PaymentStatus.UNPAID.toString())){
            appointment.setPaidAmount(appointment.getAmount());
            appointment.setPaymentStatus(PaymentStatus.PAID);
            appointmentRepository.save(appointment);
            return;
        }
        if(appointment.getPaymentStatus().name().equals(PaymentStatus.PAID.toString())){
            appointment.setPaidAmount(0);
            appointment.setPaymentStatus(PaymentStatus.UNPAID);
            appointmentRepository.save(appointment);
        }
    }
}
