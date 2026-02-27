package com.hostelmanagement.hostel.service;

import com.hostelmanagement.hostel.dto.OwnerTenantDto;
import com.hostelmanagement.hostel.dto.TenantCreateDto;
import com.hostelmanagement.hostel.model.*;
import com.hostelmanagement.hostel.repo.BedAllocationRepository;
import com.hostelmanagement.hostel.repo.BedRepository;
import com.hostelmanagement.hostel.repo.TenantOnboardingRepository;
import com.hostelmanagement.hostel.repo.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TenantOnboardingService {

    private final TenantOnboardingRepository onboardingRepository;
    private final TenantRepository tenantRepository;
    private  final BedRepository bedRepository;
    private final BedAllocationRepository bedAllocationRepository;

    public TenantOnboardingService(TenantOnboardingRepository onboardingRepository,
                                   TenantRepository tenantRepository,
                                   BedRepository bedRepository,
                                   BedAllocationRepository bedAllocationRepository) {
        this.onboardingRepository = onboardingRepository;
        this.tenantRepository = tenantRepository;
        this.bedRepository = bedRepository;
        this.bedAllocationRepository = bedAllocationRepository;
    }

    @Transactional
    public String createOnboarding(OwnerTenantDto dto) {

        String token = UUID.randomUUID().toString();

        TenantOnboarding onboarding = new TenantOnboarding();
        onboarding.setTenantName(dto.getTenantName());
        onboarding.setMobile(dto.getMobile());
        onboarding.setBedId(dto.getBedId());
        onboarding.setAgreedMonthlyFee(dto.getMonthlyFee());
        onboarding.setAgreedMaintenanceFee(dto.getMaintenanceFee());
        onboarding.setToken(token);

         onboardingRepository.save(onboarding);

        return token;
    }

    @Transactional
    public Tenant completeRegistration(String token, TenantCreateDto dto) {

        TenantOnboarding onboarding = onboardingRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid link"));

        if (onboarding.isCompleted()) {
            throw new RuntimeException("Link already used");
        }

        Tenant tenant = new Tenant();
        tenant.setName(onboarding.getTenantName());
        tenant.setMobile(onboarding.getMobile());
        tenant.setEmail(dto.getEmail());
        tenant.setStatus(TenantStatus.ACTIVE.name());
        tenant.setProfileCompleted(true);

        Tenant savedTenant = tenantRepository.save(tenant);

        Bed bed = bedRepository.findById(onboarding.getBedId())
                .orElseThrow(() -> new RuntimeException("Bed not found"));

        bed.setStatus(BedStatus.OCCUPIED);
        bedRepository.save(bed);

        BedAllocation allocation = new BedAllocation();
        allocation.setTenantId(savedTenant.getId());
        allocation.setBedId(bed.getId());
        allocation.setAgreedMonthlyFee(onboarding.getAgreedMonthlyFee());
        allocation.setAgreedMaintenanceFee(onboarding.getAgreedMaintenanceFee());
        allocation.setStartDate(LocalDate.now());
        allocation.setStatus(AllocationStatus.ACTIVE);

        bedAllocationRepository.save(allocation);

        onboarding.setCompleted(true);

        return savedTenant;
    }

    public TenantOnboarding getOnboarding(String token) {
        TenantOnboarding onboarding = onboardingRepository
                .findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid onboarding link"));

        return onboarding;
    }
}
