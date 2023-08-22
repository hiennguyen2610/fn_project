package com.example.hospital_management.repository;


import com.example.hospital_management.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Long> {
    Optional<Otp> findByOtpCode (String otp);
}
