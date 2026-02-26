package com.hostelmanagement.hostel.controller;


import com.hostelmanagement.hostel.dto.HostelCreateDto;
import com.hostelmanagement.hostel.dto.HostelUpdateDto;
import com.hostelmanagement.hostel.model.Hostel;
import com.hostelmanagement.hostel.service.HostelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hostel")
public class HostelController {

    private HostelService hostelService;

    public HostelController(HostelService hostelService) {
        this.hostelService= hostelService;
    }

    @PostMapping("/create")
    public ResponseEntity<Hostel> create(@RequestBody HostelCreateDto hostelCreateDto) {
        return ResponseEntity.ok(hostelService.createHostel(hostelCreateDto));
    }


    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Hostel>> getOwnerHostels(
            @PathVariable Long ownerId) {

        return ResponseEntity.ok(hostelService.getHostelsByOwner(ownerId));
    }

    @PutMapping("/update")
    public ResponseEntity<Hostel> update(@RequestBody HostelUpdateDto hostelUpdateDto){
        return ResponseEntity.ok(hostelService.updateHostel(hostelUpdateDto));
    }

}
