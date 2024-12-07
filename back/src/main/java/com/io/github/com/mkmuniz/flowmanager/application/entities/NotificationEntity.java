package com.io.github.com.mkmuniz.flowmanager.application.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer pixId;
    
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime receivedAt;
}
