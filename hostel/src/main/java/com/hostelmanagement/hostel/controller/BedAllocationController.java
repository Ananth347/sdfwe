package com.hostelmanagement.hostel.controller;

import com.hostelmanagement.hostel.dto.AllocateBedDto;
import com.hostelmanagement.hostel.service.BedAllocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/allocations")
public class BedAllocationController {

    private final BedAllocationService service;

    public BedAllocationController(BedAllocationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> allocateBed(
            @RequestBody AllocateBedDto dto) {

        return ResponseEntity.ok(service.allocateBed(dto));
    }
}