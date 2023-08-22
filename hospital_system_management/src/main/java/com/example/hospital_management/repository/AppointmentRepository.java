package com.example.hospital_management.repository;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.statics.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a where a.doctor.id = ?1 and a.appointmentDate = ?2")
    public List<Appointment> findByDoctor(Long doctorId, Date date);

    @Query("select a from Appointment a where a.doctor.user.id = ?1 and a.appointmentDate = ?2")
    public List<Appointment> findByDoctorUser(Long userId, Date date);

    @Query("select a from Appointment a where a.patient.email = ?1 or a.patient.phone = ?1")
    public List<Appointment> myAppointment(String param);

    @Query("select a from Appointment a where a.appointmentDate >= ?1 and a.appointmentDate <= ?2 and (a.patient.name like ?3 or a.patient.email like ?3 or a.patient.phone like ?3)")
    public Page<Appointment> findByAdmin(Date start, Date end, String param, Pageable pageable);

    @Query("select a from Appointment a where a.appointmentStatus <> ?4 and a.appointmentDate >= ?1 and a.appointmentDate <= ?2 and (a.patient.name like ?3 or a.patient.email like ?3 or a.patient.phone like ?3)")
    public Page<Appointment> findByEmp(Date start, Date end, String param, AppointmentStatus appointmentStatus, Pageable pageable);


    @Query("select a from Appointment a where a.appointmentDate >= ?1 and a.appointmentDate <= ?2 and" +
            " (a.patient.name like ?3 or a.patient.email like ?3 or a.patient.phone like ?3)" +
            " and a.doctor.user.id = ?4 and (a.appointmentStatus = ?5 or a.appointmentStatus = ?6)")
    public Page<Appointment> findByDoctor(Date start, Date end, String param, Long userId, AppointmentStatus appointmentStatus,AppointmentStatus appointmentStatus2, Pageable pageable);
}
