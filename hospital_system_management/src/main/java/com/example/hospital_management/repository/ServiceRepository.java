package com.example.hospital_management.repository;
import com.example.hospital_management.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    boolean existsByName(String name);

}
