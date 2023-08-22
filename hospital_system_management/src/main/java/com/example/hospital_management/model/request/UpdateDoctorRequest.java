package com.example.hospital_management.model.request;

import com.example.hospital_management.statics.DoctorLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateDoctorRequest {
    String address;

    String phone;

    String introduce;

    String avatar;

    DoctorLevel doctorLevel;

    long[] specialityIds;
}
