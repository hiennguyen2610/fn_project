package com.example.hospital_management.entity;

import com.example.hospital_management.statics.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification extends BaseEntity {

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "from_to_id")
    User formUser;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "to_user_id")
    User toUser;

    @Column(name = "content")
    String content;

    @Column(name = "notification_status")
    @Enumerated(EnumType.STRING)
    NotificationStatus notificationStatus;
}
