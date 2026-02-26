package com.hostelmanagement.hostel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class BedAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bedId;
    private Long tenantId;

    private Double agreedMonthlyFee;
    private Double agreedMaintenanceFee;

    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private AllocationStatus status;
}


