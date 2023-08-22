package com.example.hospital_management.config;

import com.example.hospital_management.repository.RefreshTokenRepository;
import com.example.hospital_management.security.SecurityUtils;
import com.example.hospital_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class LogoutSuccessHandle implements LogoutSuccessHandler {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Cookie jwtCookie = new Cookie("jwtToken", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);
        Optional<String> userIdOptional = SecurityUtils.getCurrentUserLogin();
        Optional<String> jwt = SecurityUtils.getCurrentUserJWT();
        if(userIdOptional.isPresent()){
            System.out.println("========> username log: "+userIdOptional);
        }
        if(jwt.isPresent()){
            System.out.println("========> jwt: "+jwt);
        }
        SecurityContextHolder.clearContext();
        response.sendRedirect("/login");
    }


}
