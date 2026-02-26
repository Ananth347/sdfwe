package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.dto.TenantCreateDto;
import com.hostelmanagement.hostel.model.Tenant;
import com.hostelmanagement.hostel.service.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/create")
    public ResponseEntity<Tenant> create(@RequestBody TenantCreateDto tenantCreateDto) {
        return ResponseEntity.ok(tenantService.createTenant(tenantCreateDto));
    }
}
