package com.hostelmanagement.hostel.service;

import com.hostelmanagement.hostel.controller.TenantOnboarding;
import com.hostelmanagement.hostel.model.Tenant;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class TenantOnboardingService {

    private final TenantOnboardingRepository onboardingRepository;

    public TenantOnboardingService(TenantOnboardingRepository onboardingRepository) {
        this.onboardingRepository = onboardingRepository;
    }

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

        TenantOnboarding onboarding =
                onboardingRepository.findByToken(token);

        Tenant tenant = new Tenant();
        tenant.setName(onboarding.getTenantName());
        tenant.setMobile(onboarding.getMobile());
        tenant.setEmail(dto.getEmail());
        tenant.setStatus("ACTIVE");
        tenant.setProfileCompleted(true);

        Tenant savedTenant = tenantRepository.save(tenant);

        Bed bed = bedRepository.findById(onboarding.getBedId()).get();
        bed.setStatus(BedStatus.OCCUPIED);

        BedAllocation allocation = new BedAllocation();
        allocation.setTenantId(savedTenant.getId());
        allocation.setBedId(bed.getId());
        allocation.setAgreedMonthlyFee(onboarding.getAgreedMonthlyFee());
        allocation.setAgreedMaintenanceFee(onboarding.getAgreedMaintenanceFee());
        allocation.setStartDate(LocalDate.now());

        allocationRepository.save(allocation);

        onboarding.setCompleted(true);

        return savedTenant;
    }
}
