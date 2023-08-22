//package com.example.hospital_management.entity;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import javax.persistence.*;
//
//@Data
//@Entity
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "service_specialities")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class ServiceSpeciality {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", nullable = false)
//    Long id;
//
//    @ManyToOne(targetEntity = Speciality.class)
//    @JoinColumn(name = "speciality_id")
//    Speciality speciality;
//
//    @ManyToOne(targetEntity = Service.class)
//    @JoinColumn(name = "service_id")
//    Service service;
//
//}
