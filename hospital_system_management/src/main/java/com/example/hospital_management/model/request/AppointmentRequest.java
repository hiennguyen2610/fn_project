package com.example.hospital_management.model.request;

import com.example.hospital_management.config.SqlTimeDeserializer;
import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Patient;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.statics.AppointmentStatus;
import com.example.hospital_management.statics.HealthInsuranceType;
import com.example.hospital_management.statics.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

@Getter
@Setter
public class AppointmentRequest {

    Doctor doctor;

    Speciality speciality;

    Date appointmentDate;

    @JsonFormat(pattern = "HH:mm")
    @JsonDeserialize(using = SqlTimeDeserializer.class)
    Time appointmentTime;

    String symptom;

    String email;

    String name;

    String phone;

    String address;

    LocalDate dob;

    String gender;

    int healthInsuranceNumber;

    HealthInsuranceType healthInsuranceType;
}
