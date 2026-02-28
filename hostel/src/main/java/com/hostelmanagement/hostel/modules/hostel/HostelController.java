package com.hostelmanagement.hostel.modules.hostel;


import com.hostelmanagement.hostel.modules.bed.Bed;
import com.hostelmanagement.hostel.modules.bed.BedRepository;
import com.hostelmanagement.hostel.modules.bed.BedStatus;
import com.hostelmanagement.hostel.modules.hostel.dto.request.HostelCreateDto;
import com.hostelmanagement.hostel.modules.hostel.dto.request.HostelUpdateDto;
import com.hostelmanagement.hostel.modules.owner.Owner;
import com.hostelmanagement.hostel.modules.owner.OwnerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
