package com.example.hospital_management.model.response;

import com.example.hospital_management.entity.BaseEntity;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.entity.User;
import com.example.hospital_management.statics.DoctorLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class DoctorResponse {
    Long id;

    User user;

    String phone;

    Set<Speciality> specialities = new LinkedHashSet<>();

    String address;

    String introduce;

    LocalDate dob;

    String doctorLevel;

    String gender;

//    @Column(name = "speciality")
//    @JoinColumn(name = "speciality_id")
//    Speciality speciality;
}
