package com.example.hospital_management.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_verifications")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OtpVerification extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "otp_id")
    Otp otp;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    boolean success;

    @Column(nullable = false)
    LocalDateTime timesVerification;

}
