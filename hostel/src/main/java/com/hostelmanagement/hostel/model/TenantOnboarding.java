package com.hostelmanagement.hostel.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class TenantOnboarding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantName;
    private String mobile;

    private Long bedId;

    private Double agreedMonthlyFee;
    private Double agreedMaintenanceFee;

    private String token;

    private LocalDateTime createdAt;

    private boolean completed;
}
