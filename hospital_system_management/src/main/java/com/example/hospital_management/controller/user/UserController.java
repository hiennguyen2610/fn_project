package com.example.hospital_management.controller.user;

import com.example.hospital_management.exception.ActivatedAccountException;
import com.example.hospital_management.exception.ExistedUserException;
import com.example.hospital_management.exception.OtpExpiredException;
import com.example.hospital_management.model.request.CreateUserRequest;
import com.example.hospital_management.model.request.EmailRequest;
import com.example.hospital_management.model.request.ResetPasswordRequest;
import com.example.hospital_management.model.response.UserResponse;
import com.example.hospital_management.service.UserService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponse getDetail(@PathVariable Long id) throws ClassNotFoundException {
        return userService.getDetail(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateUserRequest request) {
        try {
            userService.createUser(request);
            return ResponseEntity.ok(null);
        } catch (ExistedUserException ex) {
            return new ResponseEntity<>("username đã tồn tại", HttpStatus.BAD_REQUEST);
        }
    }

    // Gửi email kích hoạt tài khoản
    @GetMapping("/active-account/{email}")
    public ModelAndView activeAccount(@PathVariable("email") String email) throws ActivatedAccountException {
        try {
            userService.activeAccount(email);
            return new ModelAndView("account/notification-activation.html");
        } catch (ActivatedAccountException e) {
            return new ModelAndView("account/activation-error.html");
        }
    }

    // Gửi email đổi mật khẩu
    @PostMapping("/otp-sending")
    public ResponseEntity<?> sendOtp(@RequestBody @Valid EmailRequest emailRequest) {
        return userService.findByEmailAndActivated(emailRequest.getEmail())
                .map(user -> {
                    userService.sendOtp(emailRequest.getEmail());
                    return new ResponseEntity<>(null, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>("Email not exist or not activated", HttpStatus.NOT_FOUND));
    }

    // Api đổi mật khẩu
    @PutMapping("/password-reset")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws OtpExpiredException {
        userService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>("Change password successful", HttpStatus.OK);
    }
}
