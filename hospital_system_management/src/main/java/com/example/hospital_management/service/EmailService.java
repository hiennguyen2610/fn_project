package com.example.hospital_management.service;

import com.example.hospital_management.entity.Otp;
import com.example.hospital_management.entity.User;
import com.example.hospital_management.repository.OtpRepository;
import com.example.hospital_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OtpRepository otpRepository;

    @Value("${spring.mail.username}")
    private String sender;

    Random rd = new Random();


    @Async
    public void sendActivationEmail(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject("[Mediplus] Kích Hoạt Tài Khoản");

            String activationLink = "http://localhost:8080/api/v1/users/active-account/" + email;
//            String activationLink = "http://localhost:8080/api/v1/doctors/active-account/" + id
//                    + "/activations";
            String emailContent = "Bạn đã được tạo tài khoản tại Mediplus, ấn <a href=\"" + activationLink + "\">đây</a> để kích hoạt tài khoản của bạn.";
            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("Error while sending mail!!!");

        }
    }

    public void sendOtp(String email) {
        String otpCode = UUID.randomUUID().toString();
        Optional<User> emailOptional = userRepository.findByEmail(email);

        otpRepository.save(Otp.builder()
                .otpCode(otpCode)
                .user(emailOptional.get())
                .expiredAt(LocalDateTime.now().plusMinutes(10))
                .build());

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email);

            mimeMessageHelper.setSubject("[Mediplus] Thay đổi mật khẩu");

            String resetLink = "http://localhost:8080/check-otp-reset?otpCode=" + otpCode;
            String htmlContent = "<html> Bạn đã quên mật khẩu? <a href=\"" + resetLink + "\">Reset password.</a> </html>\n" +
                    "Email này chỉ có hiệu lực trong vòng 10 phút. Nếu đã quá thời gian vui lòng gửi lại yêu cầu.";
            mimeMessageHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error while sending mail!!!");
        }
    }
}
