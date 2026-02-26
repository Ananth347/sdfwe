package com.hostelmanagement.hostel.controller;


import com.hostelmanagement.hostel.dto.OwnerLoginDto;
import com.hostelmanagement.hostel.dto.OwnerRegisterDto;
import com.hostelmanagement.hostel.model.Owner;
import com.hostelmanagement.hostel.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
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

    @PostMapping("/login")
    public ResponseEntity<OwnerLoginDto> ownerLogin(@RequestBody OwnerLoginDto ownerLoginDto) {
        return ResponseEntity.ok(ownerService.ownerLogin(ownerLoginDto));
    }
}