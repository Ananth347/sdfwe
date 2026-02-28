package com.hostelmanagement.hostel.modules.hostel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hostelmanagement.hostel.modules.bed.Bed;
import com.hostelmanagement.hostel.modules.owner.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "hostel")
public class Hostel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonIgnore
    private Owner owner;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Bed> beds;

    @Column(name = "total_beds", nullable = false)
    private int totalBeds;

    private Double defaultMonthlyFee;
    private Double defaultMaintenanceFee;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // getters & setters
}
