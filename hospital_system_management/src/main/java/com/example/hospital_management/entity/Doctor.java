package com.example.hospital_management.entity;

import com.example.hospital_management.statics.DoctorLevel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctors")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Doctor extends BaseEntity {

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "phone")
    String phone;

    @ManyToMany
    @JoinTable(name = "doctor_speciality",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    Set<Speciality> specialities = new LinkedHashSet<>();

    @Column(name = "address")
    String address;

    @Column(name = "introduce")
    String introduce;

    @Column(name = "dob")
    LocalDate dob;

    @Column(name = "level")
    DoctorLevel doctorLevel;

//    @Column(name = "speciality")
//    @JoinColumn(name = "speciality_id")
//    Speciality speciality;
}
