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

    APPOINTMENT_CREATED("Đã khởi tạo"),
    ADMIN_APPROVED("Admin đã phê duyệt"),
    DOCTOR_REJECTED("Bác sĩ từ chối"),
    CANCELLED("Đã hủy");


    public String name;

    @JsonValue
    public String toName() {
        return name;
    }

}
