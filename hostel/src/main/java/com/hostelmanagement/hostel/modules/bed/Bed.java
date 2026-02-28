package com.hostelmanagement.hostel.modules.bed;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hostelmanagement.hostel.modules.hostel.Hostel;
import com.hostelmanagement.hostel.modules.tenant.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "bed")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hostel_id")
    @JsonIgnore
    private Hostel hostel;

    @Column(name = "bed_name", nullable = true)
    private String bedName;

    @Column(name = "bed_number", nullable = false)
    private String bedNumber;

    @OneToMany(mappedBy = "bed")
    @JsonIgnore
    private List<Tenant> tenants;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BedStatus status;

    // getters & setters
}

