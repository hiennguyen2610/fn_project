package com.example.hospital_management.service;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.Diagnosis;
import com.example.hospital_management.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Diagnosis save(Diagnosis diagnosis){
        Diagnosis result = diagnosisRepository.save(diagnosis);
        return result;
    }

    public Diagnosis findByAppointment(Long appId){
        Optional<Diagnosis> diagnosis = diagnosisRepository.findByAppointment(appId);
        if(diagnosis.isPresent()){
            return diagnosis.get();
        }
        Diagnosis diagnosis1 = new Diagnosis();
        Appointment appointment = new Appointment();
        appointment.setId(appId);
        diagnosis1.setAppointment(appointment);
        Diagnosis result = diagnosisRepository.save(diagnosis1);
        return result;
    }

    public Diagnosis findByPatient(Long idPatient){
        Optional<Diagnosis> diagnosis = diagnosisRepository.findByPatient(idPatient);
        if(diagnosis.isPresent()){
            return diagnosis.get();
        }
        return new Diagnosis();
    }
}
