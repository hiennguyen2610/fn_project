package com.example.hospital_management.model.response;

import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DoctorSearchResponse {
    Long id;

    String name;

    String phone;

    String specialities;

    String address;

    String introduce;

    LocalDate dob;

    String doctorLevel;

    String gender;

    String avatar;
}
