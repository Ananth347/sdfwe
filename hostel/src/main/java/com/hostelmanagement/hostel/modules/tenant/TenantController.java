package com.hostelmanagement.hostel.modules.tenant;

import com.hostelmanagement.hostel.modules.tenant.dto.request.OwnerDashboardFilterDto;
import com.hostelmanagement.hostel.modules.tenant.dto.request.TenantCreateDto;
import com.hostelmanagement.hostel.modules.tenant.dto.request.TenantOnboardingDto;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @PostMapping("/onboard")
    public ResponseEntity<String> createOnboarding(@RequestBody TenantOnboardingDto dto) {

        return ResponseEntity.ok(tenantService.createOnboarding(dto));
    }

    @GetMapping("/{token}")
    public ResponseEntity<Tenant> getOnboardingDetails(@PathVariable String token) {

        return ResponseEntity.ok(tenantService.getOnboarding(token));
    }

    @PutMapping("/complete/{token}")
    public ResponseEntity<Tenant> completeRegistration(
            @PathVariable String token,
            @RequestBody TenantCreateDto dto) {

        return ResponseEntity.ok(tenantService.completeRegistration(token, dto));
    }

    @PostMapping("/owner/dashboard")
    public ResponseEntity<Page<Tenant>> ownerDashboard(
            @RequestBody OwnerDashboardFilterDto filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                tenantService.ownerDashboard(filter, page, size)
        );
    }
}