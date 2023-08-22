package com.example.hospital_management.service;

import com.example.hospital_management.entity.Doctor;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.exception.NotFoundException;
import com.example.hospital_management.model.request.SpecialityRequest;
import com.example.hospital_management.repository.DoctorRepository;
import com.example.hospital_management.repository.SpecialityRepository;
import lombok.AllArgsConstructor;
import org.sonatype.sisu.siesta.common.error.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SpecialityService {

    DoctorRepository doctorRepository;
    SpecialityRepository specialityRepository;

    public List<Speciality> getAllSpecialities() {
        return specialityRepository.findAll();
    }

    public Speciality create(String name, String description) throws RuntimeException {
        if (specialityRepository.existsByName(name)) {
            throw new RuntimeException("Chuyên khoa đã tồn tại");
        }
        Speciality speciality = new Speciality();
        speciality.setName(name);
        speciality.setDescription(description);
        return specialityRepository.save(speciality);
    }

    public Page<Speciality> getAllSpecialityPage(Integer page, Integer pageSize) {
        Pageable pageRequest = PageRequest.of(page -1, pageSize);
        return specialityRepository.findAll(pageRequest);
    }

    public Speciality getSpeciality(Integer id) {
        return specialityRepository.findById(id.longValue()).orElse(null);
    }

    public Speciality update(Integer id, String name, String description) throws RuntimeException {
        Speciality speciality = getSpeciality(id);
        for (Speciality s : specialityRepository.findAll()) {
            if (s.getName().equals(name)) {
                throw new RuntimeException("Tên đã tồn tại rồi");
            }
        }
        speciality.setName(name);
        speciality.setDescription(description);
        return specialityRepository.save(speciality);
    }

    public void deleteSpeciality(Long id) throws BadRequestException {
        Optional<Speciality> specialityOptional = specialityRepository.findById(id);
        if (specialityOptional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy chuyên khoa");
        }
        List<Doctor> doctorList = doctorRepository.findAllBySpecialities(specialityOptional.get());
        if (doctorList.size() > 0) {
            throw new BadRequestException("Không thể xóa vì đang có bác sĩ trong chuyên khoa này");
        }
        specialityRepository.deleteById(id);
    }

    public List<Speciality> findAll(){
        return specialityRepository.findAll();
    }
}
