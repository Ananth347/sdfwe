package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.dto.OwnerTenantDto;
import com.hostelmanagement.hostel.dto.TenantCreateDto;
import com.hostelmanagement.hostel.model.Tenant;
import com.hostelmanagement.hostel.model.TenantOnboarding;
import com.hostelmanagement.hostel.service.TenantOnboardingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/onboarding")
public class TenantOnboardingController {

    private final TenantOnboardingService onboardingService;

    public TenantOnboardingController(TenantOnboardingService onboardingService) {
        this.onboardingService = onboardingService;
    }

    @GetMapping("/{token}")
    public TenantOnboarding getOnboardingDetails(@PathVariable String token) {
        return onboardingService.getOnboarding(token);
    }

    @PostMapping("/complete/{token}")
    public Tenant completeRegistration(
            @PathVariable String token,
            @RequestBody TenantCreateDto dto) {

        return onboardingService.completeRegistration(token, dto);
    }

    @PostMapping("/onboard")
    public String createOnboarding(@RequestBody OwnerTenantDto ownerTenantDto) {
        return onboardingService.createOnboarding(ownerTenantDto);
    }
}