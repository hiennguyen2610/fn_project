package com.example.hospital_management.model.response;

import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Doctor2Response {
    Long id;

    String name;

    String phone;

    Set<Speciality> specialities = new LinkedHashSet<>();

    String address;

    String introduce;

    LocalDate dob;

    String doctorLevel;

    String gender;
}
