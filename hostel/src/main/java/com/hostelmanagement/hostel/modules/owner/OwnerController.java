package com.hostelmanagement.hostel.modules.owner;


import com.hostelmanagement.hostel.modules.owner.dto.request.OwnerLoginDto;
import com.hostelmanagement.hostel.modules.owner.dto.request.OwnerRegisterDto;
import com.hostelmanagement.hostel.modules.owner.dto.request.OwnerUpdateDto;
import com.hostelmanagement.hostel.modules.tenant.TenantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final TenantService tenantService;

    public OwnerController(OwnerService ownerService, TenantService tenantService) {
        this.ownerService = ownerService;
        this.tenantService = tenantService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Owner>> getOwner() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @PostMapping("/register")
    public ResponseEntity<Owner> registerOwner(
            @RequestBody OwnerRegisterDto request) {

        return ResponseEntity.ok(ownerService.registerOwner(request));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Owner> update(@RequestBody OwnerUpdateDto ownerUpdateDto,
                                        @PathVariable Long id) {
        return ResponseEntity.ok(ownerService.updateOwner(ownerUpdateDto, id));
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword( @RequestBody OwnerLoginDto ownerLoginDto) {
        return ResponseEntity.ok(ownerService.forgetPassword(ownerLoginDto));
    }

    @PostMapping("/login")
    public ResponseEntity<OwnerLoginDto> ownerLogin(@RequestBody OwnerLoginDto ownerLoginDto) {
        return ResponseEntity.ok(ownerService.ownerLogin(ownerLoginDto));
    }


}