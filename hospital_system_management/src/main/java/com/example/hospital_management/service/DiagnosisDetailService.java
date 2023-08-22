package com.example.hospital_management.service;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Diagnosis;
import com.example.hospital_management.entity.DiagnosisDetail;
import com.example.hospital_management.entity.Service;
import com.example.hospital_management.exception.MessageException;
import com.example.hospital_management.model.request.DiagnosisDetailRequest;
import com.example.hospital_management.repository.AppointmentRepository;
import com.example.hospital_management.repository.DiagnosisDetailRepository;
import com.example.hospital_management.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class DiagnosisDetailService {

    @Autowired
    private DiagnosisDetailRepository diagnosisDetailRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    public void addServiceToAppointment(Long idAppointment, List<String> listIdService){
        Optional<Appointment> appointment = appointmentRepository.findById(idAppointment);
        if(appointment.isEmpty()){
            throw new MessageException("appointment not found", 404);
        }
        diagnosisDetailRepository.deleteByApp(idAppointment);
        Integer amount = 0;
        for(String idSv : listIdService){
            Optional<Service> s = serviceRepository.findById(Long.valueOf(idSv));
            if (s.isEmpty()){
                throw new MessageException("service: "+idSv+" not found", 404);
            }
            DiagnosisDetail detail = new DiagnosisDetail();
            detail.setService(s.get());
            detail.setAppointment(appointment.get());
            detail.setPrice(s.get().getPrice());
            diagnosisDetailRepository.save(detail);
            amount = amount + s.get().getPrice();
        }
        appointment.get().setAmount(amount);
        appointmentRepository.save(appointment.get());
    }

    public List<DiagnosisDetail> findByApp(Long idApp){
        return diagnosisDetailRepository.findAllByAppointment(idApp);
    }

    public List<DiagnosisDetail> findByPatient(Long idPatient){
        return diagnosisDetailRepository.findAllByPatients(idPatient);
    }

    public void update(List<DiagnosisDetailRequest> list){
        for(DiagnosisDetailRequest d : list){
            DiagnosisDetail detail = diagnosisDetailRepository.findById(d.getId()).get();
            detail.setDetail(d.getDetail());
            detail.setResult(d.getResult());
            diagnosisDetailRepository.save(detail);
        }
    }
}
