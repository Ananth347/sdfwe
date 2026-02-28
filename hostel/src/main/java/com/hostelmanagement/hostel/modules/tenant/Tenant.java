package com.hostelmanagement.hostel.modules.tenant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hostelmanagement.hostel.modules.bed.Bed;
import com.hostelmanagement.hostel.modules.payment.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantName;

    private String mobile;

    private String email;

    @Enumerated(EnumType.STRING)
    private TenantStatus status; // PENDING / ACTIVE / VACATED

    private Boolean profileCompleted = false;

    @ManyToOne
    @JoinColumn(name = "bed_id")
    @JsonIgnore
    private Bed bed;

    @OneToMany(mappedBy = "tenant")
    @JsonIgnore
    private List<Payment> payments;

    private Double monthlyFee;
    private Double maintenanceFee;

    private String token;

    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }
}