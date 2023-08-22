package com.example.hospital_management.entity;

import com.example.hospital_management.statics.HealthInsuranceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patients")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient extends BaseEntity {

    @Column(name = "email")
    String email;

    @Column(name = "name")
    String name;

    @Column(name = "phone")
    String phone;

    @Column(name = "address")
    String address;

    @Column(name = "dob")
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate dob;

    String gender;

    @Column(name = "symptom")
    String symptom;

    @Column(name = "health_insurance_number")
    int healthInsuranceNumber;

    @Column(name = "health_insurance_type")
    HealthInsuranceType healthInsuranceType;
}
