package com.example.hospital_management.entity;

import com.example.hospital_management.config.SqlTimeDeserializer;
import com.example.hospital_management.statics.AppointmentStatus;
import com.example.hospital_management.statics.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Appointment extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    @ManyToOne
    @JoinColumn(name = "speciality_id")
    Speciality speciality;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date appointmentDate;

    @JsonFormat(pattern = "HH:mm")
    @JsonDeserialize(using = SqlTimeDeserializer.class)
    Time appointmentTime;

    @Column(name = "status")
    AppointmentStatus appointmentStatus;

    @Column(name = "symptom")
    String symptom;

    @Column(name = "amount")
    Integer amount;

    @Column(name = "payment_status")
    PaymentStatus paymentStatus;

    @Column(name = "paid_amount")
    Integer paidAmount;

}
