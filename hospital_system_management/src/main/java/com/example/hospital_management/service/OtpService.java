package com.example.hospital_management.service;

import com.example.hospital_management.repository.OtpRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpService {

    final OtpRepository otpRepository;

    final ObjectMapper objectMapper;

    public String generateOTP() {
        StringBuilder otp = new StringBuilder();

        String OTP_CHARACTERS = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM";

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(OTP_CHARACTERS.length());
            otp.append(OTP_CHARACTERS.charAt(index));
        }

        return otp.toString();
    }

//    public OtpResponse verifyOtp(String otpCode) {
//        Optional<Otp> optional=otpRepository.findByOtpCode(otpCode);
//        if (optional.isPresent()){
//            Otp otp=optional.get();
//            return OtpResponse.builder()
//                    .otpCode(otp.getOtpCode())
//                    .expiredTime(otp.getExpiredTime())
//                    .email(otp.getUser().getEmail())
//                    .build();
//        }
//        return null;
//    }
}
