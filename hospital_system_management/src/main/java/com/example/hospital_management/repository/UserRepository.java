package com.example.hospital_management.repository;


import com.example.hospital_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<Object> findByEmailAndActivated(String email, boolean activated);

    Optional<User> findByNameContaining(String name);

}