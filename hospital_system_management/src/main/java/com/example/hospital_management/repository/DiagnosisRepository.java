package com.example.hospital_management.repository;

import com.example.hospital_management.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    @Query("select d from Diagnosis d where d.appointment.id = ?1")
    public Optional<Diagnosis> findByAppointment(Long appId);

    @Query("select d from Diagnosis d where d.appointment.patient.id = ?1")
    public Optional<Diagnosis> findByPatient(Long idPatient);
}
