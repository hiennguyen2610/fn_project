package com.example.hospital_management.repository;

import com.example.hospital_management.entity.Role;
import com.example.hospital_management.statics.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);

}
