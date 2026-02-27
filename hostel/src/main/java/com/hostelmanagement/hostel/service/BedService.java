package com.hostelmanagement.hostel.service;


import com.hostelmanagement.hostel.dto.BedUpdateDto;
import com.hostelmanagement.hostel.model.Bed;
import com.hostelmanagement.hostel.model.BedStatus;
import com.hostelmanagement.hostel.repo.BedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class BedService {

    private final BedRepository bedRepository;

    public BedService(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    // ================================
    // UPDATE BED COUNT
    // ================================
    @Transactional
    public List<Bed> updateBedCount(Long hostelId, int newBedCount) {

        List<Bed> existingBeds = bedRepository.findByHostelId(hostelId);
        int currentBedCount = existingBeds.size();

        if (newBedCount == currentBedCount) {
            return existingBeds;
        }

        // INCREASE BED COUNT
        if (newBedCount > currentBedCount) {

            List<Bed> newBeds = new ArrayList<>();

            for (int i = currentBedCount + 1; i <= newBedCount; i++) {

                Bed bed = new Bed();
                bed.setHostelId(hostelId);
                bed.setBedNumber("BED-" + i);
                bed.setBedName("BED-" + i);
                bed.setStatus(BedStatus.AVAILABLE);

                newBeds.add(bed);
            }

            bedRepository.saveAll(newBeds);
            return bedRepository.findByHostelId(hostelId);
        }

        // DECREASE BED COUNT
        int bedsToRemove = currentBedCount - newBedCount;

        List<Bed> removableBeds = existingBeds.stream()
                .filter(bed -> bed.getStatus() == BedStatus.AVAILABLE)
                .sorted(Comparator.comparing(Bed::getId).reversed())
                .limit(bedsToRemove)
                .toList();

        if (removableBeds.size() < bedsToRemove) {
            throw new RuntimeException(
                    "Cannot reduce beds. Some beds are occupied.");
        }

        bedRepository.deleteAll(removableBeds);

        return bedRepository.findByHostelId(hostelId);
    }

    // ================================
    // UPDATE BED NAMES
    // ================================
    @Transactional
    public List<Bed> updateBedNames(List<BedUpdateDto> dtoList) {

        if (dtoList == null || dtoList.isEmpty()) {
            throw new RuntimeException("Bed update list cannot be empty");
        }

        List<Long> bedIds = dtoList.stream()
                .map(BedUpdateDto::getId)
                .toList();

        List<Bed> beds = bedRepository.findAllById(bedIds);

        Map<Long, String> updateMap = dtoList.stream()
                .collect(Collectors.toMap(
                        BedUpdateDto::getId,
                        BedUpdateDto::getBedName
                ));

        for (Bed bed : beds) {

            if (bed.getStatus() != BedStatus.AVAILABLE) {
                throw new RuntimeException(
                        "Cannot rename occupied bed: " + bed.getId());
            }

            String newName = updateMap.get(bed.getId());

            if (newName != null && !newName.equals(bed.getBedName())) {
                bed.setBedName(newName);
            }
        }

        return bedRepository.saveAll(beds);
    }
}