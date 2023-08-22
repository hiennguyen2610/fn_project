package com.example.hospital_management.repository;

import com.example.hospital_management.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query(value = "SELECT p.* from appointments a \n" +
            "INNER JOIN diagnosis d on d.appointment_id = a.id\n" +
            "INNER join patients p on p.id = a.patient_id\n" +
            " where p.name like ?1 or p.phone = ?1 or p.address like ?1 or p.email = ?1", nativeQuery = true)
    public Page<Patient> findAlls(String search, Pageable pageable);
}
