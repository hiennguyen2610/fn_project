package com.example.hospital_management.config;

import com.example.hospital_management.entity.Role;
import com.example.hospital_management.entity.User;
import com.example.hospital_management.repository.RoleRepository;
import com.example.hospital_management.repository.UserRepository;
import com.example.hospital_management.statics.Roles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseInitializer implements CommandLineRunner {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<Role> roleUserOptinal = roleRepository.findByName(Roles.USER);
        if(roleUserOptinal.isEmpty()){
            Role userRole = Role.builder().name(Roles.USER).build();
            roleRepository.save(userRole);
        }
        Optional<Role> roleDoctorOptinal = roleRepository.findByName(Roles.DOCTOR);
        if(roleDoctorOptinal.isEmpty()){
            Role doctorRole = Role.builder().name(Roles.DOCTOR).build();
            roleRepository.save(doctorRole);
        }

        Optional<Role> roleAdminOptinal = roleRepository.findByName(Roles.ADMIN);
        if(roleAdminOptinal.isEmpty()){
            Role adminRole = Role.builder().name(Roles.ADMIN).build();
            roleRepository.save(adminRole);

            Optional<User> admin = userRepository.findByEmail("admin@gmail.com");
            if(admin.isEmpty()){
                User user = new User();
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin123")); // Encrypt the password
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);
                user.setRoles(roles);
                userRepository.save(user);
            }
        }
    }

}
