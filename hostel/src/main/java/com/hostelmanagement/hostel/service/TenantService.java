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

        Tenant tenant = new Tenant();
        tenant.setName(dto.getName());
        tenant.setMobile(dto.getMobile());
        tenant.setEmail(dto.getEmail());
        tenant.setStatus(dto.getStatus());
        tenant.setProfileCompleted(dto.getProfileCompleted());

        return tenantRepository.save(tenant);
    }
}
