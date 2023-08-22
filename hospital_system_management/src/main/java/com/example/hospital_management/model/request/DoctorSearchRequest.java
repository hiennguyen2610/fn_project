package com.example.hospital_management.model.request;

import lombok.Data;

@Data
public class DoctorSearchRequest extends BaseSearchRequest{

    String all;
    String name;
    String speciality;

}
