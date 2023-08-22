package com.example.hospital_management.model.request;

import com.example.hospital_management.entity.DoctorSpeciality;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.statics.DoctorLevel;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

    String name;

    String address;

    String phone;

    String introduce;

    String avatar;

    DoctorLevel doctorLevel;

    @NotNull(message = "Danh sách chuyên khoa không được trống")
    @Size(min = 1, message = "Phải có ít nhất một chuyên khoa")
    long[] specialityIds;

//    @NotBlank
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    @Past
    LocalDate dob;

    String gender;

    @NotBlank
    @Size(max = 50)
    String email;

    @NotBlank
    String password;

}
