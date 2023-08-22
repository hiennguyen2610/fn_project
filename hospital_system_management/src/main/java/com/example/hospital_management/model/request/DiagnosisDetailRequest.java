package com.example.hospital_management.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DiagnosisDetailRequest {

    private Long id;

    private String detail;

    private String result;
}
