package com.hostelmanagement.hostel.service;


import com.hostelmanagement.hostel.dto.BedUpdateDto;
import com.hostelmanagement.hostel.model.Bed;
import com.hostelmanagement.hostel.repo.BedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BedService {

    private final BedRepository bedRepository;

    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    @Transactional
    public List<Bed> updateBed(List<BedUpdateDto> dtoList) {

        if (dtoList == null || dtoList.isEmpty()) {
            throw new RuntimeException("Bed update list cannot be empty");
        }

        Long hostelId = dtoList.get(0).getHostelId();

        // 1. Fetch all beds for hostel
        List<Bed> totalBeds = bedRepository.findByHostelId(hostelId);

        // 2. Convert DTO list to Map (bedId -> newBedName)
        Map<Long, String> updateMap = dtoList.stream()
                .collect(Collectors.toMap(
                        BedUpdateDto::getId,
                        BedUpdateDto::getBedName
                ));

        // 3. Update bed names only if changed
        for (Bed bed : totalBeds) {
            if (updateMap.containsKey(bed.getId())) {

                String newName = updateMap.get(bed.getId());

                if (!bed.getBedName().equals(newName)) {
                    bed.setBedName(newName);
                }
            }
        }

        // 4. Save all updated beds
        return bedRepository.saveAll(totalBeds);
    }

}