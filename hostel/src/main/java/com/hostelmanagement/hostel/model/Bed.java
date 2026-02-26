package com.hostelmanagement.hostel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "bed")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hostel_id", nullable = false)
    private Long hostelId;

    @Column(name = "bed_name", nullable = true)
    private String bedName;

    @Column(name = "bed_number", nullable = false)
    private String bedNumber;

    private Double monthlyFee;
    private Double maintenanceFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BedStatus status;

    // getters & setters
}

