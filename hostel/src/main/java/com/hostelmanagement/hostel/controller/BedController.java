package com.hostelmanagement.hostel.controller;


import com.hostelmanagement.hostel.dto.BedUpdateDto;
import com.hostelmanagement.hostel.model.Bed;
import com.hostelmanagement.hostel.service.BedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beds")
public class BedController {

    private final BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PutMapping("/names")
    public ResponseEntity<List<Bed>> updateBedNames(
            @RequestBody List<BedUpdateDto> dtoList) {

        return ResponseEntity.ok(bedService.updateBedNames(dtoList));
    }

    @PutMapping("/count")
    public ResponseEntity<List<Bed>> updateBedCount(
            @RequestParam Long hostelId,
            @RequestParam int newBedCount) {

        return ResponseEntity.ok(
                bedService.updateBedCount(hostelId, newBedCount));
    }
}