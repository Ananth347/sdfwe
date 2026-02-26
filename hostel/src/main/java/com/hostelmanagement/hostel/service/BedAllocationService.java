package com.hostelmanagement.hostel.service;

import com.hostelmanagement.hostel.dto.AllocateBedDto;
import com.hostelmanagement.hostel.model.*;
import com.hostelmanagement.hostel.repo.BedAllocationRepository;
import com.hostelmanagement.hostel.repo.BedRepository;
import com.hostelmanagement.hostel.repo.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class BedAllocationService {

    private final BedRepository bedRepository;
    private final TenantRepository tenantRepository;
    private final BedAllocationRepository allocationRepository;

    public BedAllocationService(BedRepository bedRepository,
                                TenantRepository tenantRepository,
                                BedAllocationRepository allocationRepository) {
        this.bedRepository = bedRepository;
        this.tenantRepository = tenantRepository;
        this.allocationRepository = allocationRepository;
    }

    public String allocateBed(AllocateBedDto dto) {

        Bed bed = bedRepository.findById(dto.getBedId())
                .orElseThrow(() -> new RuntimeException("Bed not found"));

        if (bed.getStatus() != BedStatus.AVAILABLE) {
            throw new RuntimeException("Bed is not available");
        }

        // Create Tenant
        Tenant tenant = new Tenant();
        tenant.setName(dto.getTenantName());
        tenant.setMobile(dto.getMobile());
        tenant.setStatus(String.valueOf(TenantStatus.PENDING));

        Tenant savedTenant = tenantRepository.save(tenant);

        // Create Allocation
        BedAllocation allocation = new BedAllocation();
        allocation.setBedId(bed.getId());
        allocation.setTenantId(savedTenant.getId());
        allocation.setAgreedMonthlyFee(dto.getAgreedMonthlyFee());
        allocation.setAgreedMaintenanceFee(dto.getAgreedMaintenanceFee());
        allocation.setStartDate(LocalDate.now());
        allocation.setStatus(AllocationStatus.PENDING_PAYMENT);

        allocationRepository.save(allocation);

        return "Tenant created & allocation pending payment";
    }
}
