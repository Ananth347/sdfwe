package com.hostelmanagement.hostel.repo;

import com.hostelmanagement.hostel.model.AllocationStatus;
import com.hostelmanagement.hostel.model.BedAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BedAllocationRepository extends JpaRepository<BedAllocation, Long> {

    List<BedAllocation> findByTenantId(Long tenantId);

    List<BedAllocation> findByBedId(Long bedId);

    Optional<BedAllocation> findByBedIdAndStatus(Long bedId, AllocationStatus status);
}