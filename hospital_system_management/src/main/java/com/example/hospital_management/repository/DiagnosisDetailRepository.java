package com.example.hospital_management.repository;

import com.example.hospital_management.entity.DiagnosisDetail;
import com.example.hospital_management.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DiagnosisDetailRepository extends JpaRepository <DiagnosisDetail, Long> {

    List<DiagnosisDetail> findAllByService(Service service);

    @Query("select d from DiagnosisDetail d where d.appointment.id = ?1")
    List<DiagnosisDetail> findAllByAppointment(Long idApp);

    @Query("select d from DiagnosisDetail d where d.appointment.patient.id = ?1")
    List<DiagnosisDetail> findAllByPatients(Long idPatients);

    @Modifying
    @Transactional
    @Query("delete from DiagnosisDetail d where d.appointment.id = ?1")
    int deleteByApp(Long appId);
}
