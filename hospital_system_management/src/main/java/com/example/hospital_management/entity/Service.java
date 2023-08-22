package com.example.hospital_management.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Service extends BaseEntity {

    @Column(name = "name")
    String name;

    @Column(name = "price")
    int price;
}
