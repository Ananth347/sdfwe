package com.hostelmanagement.hostel.controller;


import com.hostelmanagement.hostel.dto.BedUpdateDto;
import com.hostelmanagement.hostel.model.Bed;
import com.hostelmanagement.hostel.service.BedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BedController {

    private BedService bedService;

    public BedController(BedService bedService) {
        this.bedService = bedService;
    }

    @PutMapping("/update")
    public ResponseEntity<List<Bed>> update(@RequestBody List<BedUpdateDto> bedUpdateDto) {
        return ResponseEntity.ok(bedService.updateBed(bedUpdateDto));
    }
}
