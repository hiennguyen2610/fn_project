package com.example.hospital_management.statics;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DoctorLevel {
    GIAO_SU("Giáo Sư"),
    PGS_TS("PGS.TS"),
    TIEN_SI("Tiến Sĩ"),
    THAC_SI("Thạc Sĩ"),
    BSCK_II("BS Chuyên Khoa II"),
    BSCK_I("BS Chuyên Khoa I");

    String name;

}
