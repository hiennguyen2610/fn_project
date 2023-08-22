package com.example.hospital_management.config;

import com.example.hospital_management.entity.Role;
import com.example.hospital_management.entity.User;
import com.example.hospital_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        String username = authentication.getName();
        User user = userRepository.findByEmail(username).get();
        System.out.println("========> user login: "+user.getEmail());
        List<String> roles = new ArrayList<>();
        for(Role r : user.getRoles()){
            roles.add(r.getName().name());
        }

        String redirectURL = request.getContextPath();

        if (checkRole("ADMIN", roles)) {
            redirectURL = "admin/appointments";
        } if (checkRole("DOCTOR", roles)) {
            redirectURL = "doctor/appointment";
        }

        else if (checkRole("USER", roles)) {
            redirectURL = "/";
        }

        response.sendRedirect(redirectURL);

    }



    public boolean checkRole(String role,List<String> list) {
        for(String s : list) {
            if(s.equals(role)) {
                return true;
            }
        }
        return false;
    }
}
