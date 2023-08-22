package com.example.hospital_management.service;

import com.example.hospital_management.entity.Appointment;
import com.example.hospital_management.entity.DiagnosisDetail;
import com.example.hospital_management.entity.Service;
import com.example.hospital_management.entity.Speciality;
import com.example.hospital_management.exception.NotFoundException;
import com.example.hospital_management.repository.DiagnosisDetailRepository;
import com.example.hospital_management.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.sonatype.sisu.siesta.common.error.BadRequestException;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class SvService {

    ServiceRepository serviceRepository;
    DiagnosisDetailRepository diagnosisDetailRepository;

    public Service getService(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }


    public Service createService(String name, int price) throws RuntimeException {
        if (serviceRepository.existsByName(name)) {
            throw new RuntimeException("Dịch vụ này đã có sẵn");
        }
        Service service = new Service();
        service.setName(name);
        service.setPrice(price);
        return serviceRepository.save(service);
    }

    public Service updateService(Long id, String name, int price) throws RuntimeException {
        Service service = getService(id);
        for (Service s : serviceRepository.findAll()) {
            if (s.getName().equals(name) && s.getId() != id) {
                throw new RuntimeException("Tên dịch vụ đã tồn tại");
            }
        }
        service.setName(name);
        service.setPrice(price);
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) throws BadRequestException {
        Optional<Service> serviceOptional = serviceRepository.findById(id);
        if (serviceOptional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy dịch vụ này!");
        }
        List<DiagnosisDetail> diagnosisDetailList = diagnosisDetailRepository.findAllByService(serviceOptional.get());
        if (diagnosisDetailList.size() > 0) {
            throw new BadRequestException("Không thể xóa dịch vụ này vì đang được sử dụng");
        }
        serviceRepository.deleteById(id);
    }
}
