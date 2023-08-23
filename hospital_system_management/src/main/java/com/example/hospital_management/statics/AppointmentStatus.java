package com.example.hospital_management.statics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum AppointmentStatus {

    APPOINTMENT_CREATED("Khởi tạo"),
    ADMIN_APPROVED("Phê duyệt"),
    DOCTOR_REJECTED("Từ chối"),
    CANCELLED("Hủy");


    public String name;

    @JsonValue
    public String toName() {
        return name;
    }

}
