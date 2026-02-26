package com.hostelmanagement.hostel.service;


import com.hostelmanagement.hostel.dto.TenantCreateDto;
import com.hostelmanagement.hostel.model.Tenant;
import com.hostelmanagement.hostel.repo.TenantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Tenant createTenant(TenantCreateDto dto) {
        Tenant existingTenant = tenantRepository.findByMobile(dto.getMobile());
        existingTenant.setEmail(dto.getEmail());
        existingTenant.setStatus(dto.getStatus());
        existingTenant.setProfileCompleted(dto.getProfileCompleted());

        return tenantRepository.save(existingTenant);
    }
}
