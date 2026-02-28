package com.hostelmanagement.hostel.modules.bed;


import com.hostelmanagement.hostel.modules.bed.dto.request.BedUpdateDto;
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

    @GetMapping("/allBed/{hostelId}")
    public ResponseEntity<List<Bed>> getBeds( @PathVariable Long hostelId) {
        return ResponseEntity.ok(bedService.getAllBeds(hostelId));
    }
}