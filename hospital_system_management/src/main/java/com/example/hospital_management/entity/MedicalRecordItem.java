package com.example.hospital_management.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_record_item")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "diagnosis_detail_id")
    DiagnosisDetail diagnosisDetail;

    @Column(name = "content")
    String content;

    @Column(name = "file_url")
    String fileUrl;

}
