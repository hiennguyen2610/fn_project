package com.example.hospital_management.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "specialities")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Speciality extends BaseEntity{

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

}
