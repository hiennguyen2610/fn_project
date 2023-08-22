package com.example.hospital_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diagnosis")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Diagnosis extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "appointment_id")
    Appointment appointment;

    @Column(name = "detail")
    String detail;

    @Column(name = "result")
    String result;

    @Column(name = "re_excamination_datetime")
    LocalDate re_excamination_datetime;
}
