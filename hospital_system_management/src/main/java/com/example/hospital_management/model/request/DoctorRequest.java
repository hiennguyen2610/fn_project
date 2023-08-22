package com.example.hospital_management.model.request;

import com.example.hospital_management.statics.DoctorLevel;
import com.example.hospital_management.statics.Gender;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FieldDefaults(level = AccessLevel.PROTECTED)

public class DoctorRequest {
    Long id;

    String email;

    String password;

    String name;

    Gender gender;

    String avatar;

    String phone;

    String address;

    String introduce;

    @DateTimeFormat(pattern = "YYYY-MM-dd")
    LocalDate dob;

    DoctorLevel doctorLevel;

    LocalDateTime deletedDateTime;

    LocalDateTime createdDateTime;

    LocalDateTime lastModifiedDateTime;

    String createdBy;

    String lastModifiedBy;

}
