package com.example.hospital_management.repository;

import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findAllBySpecialities(Speciality speciality);
//    Optional<Doctor> findByEmail(String email);

    @Query("select d from Doctor d where d.user.id = ?1")
    public Doctor findByUser(Long userId);


    @Query("select d from Doctor d inner join d.specialities sp where sp.id = ?1")
    public List<Doctor> findBySpeciality(Long idSpecialy);

    @Query("select d from Doctor d inner join d.specialities sp where sp.id = ?1 and d.id <> ?2")
    public List<Doctor> findBySpecialityAndDoctor(Long idSpecialy, Long iddoctor);

    @Query("select d from Doctor d")
    public Page<Doctor> findAllPage(Pageable pageable);

    @Query("select d from Doctor d where d.user.name like ?1 or d.address like ?1 or d.phone like ?1")
    public Page<Doctor> searchDoctorByParam(String param, Pageable pageable);

    @Query("select d from Doctor d inner join d.specialities sp where" +
            " (d.user.name like ?1 or d.address like ?1 or d.phone like ?1) and sp.id = ?2")
    public Page<Doctor> searchDoctorBySpecialy(String param,Long specialy, Pageable pageable);
}
