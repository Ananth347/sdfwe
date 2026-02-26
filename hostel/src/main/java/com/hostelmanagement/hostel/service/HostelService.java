package com.hostelmanagement.hostel.service;


import com.hostelmanagement.hostel.controller.HostelController;
import com.hostelmanagement.hostel.dto.HostelCreateDto;
import com.hostelmanagement.hostel.dto.HostelUpdateDto;
import com.hostelmanagement.hostel.model.Bed;
import com.hostelmanagement.hostel.model.BedStatus;
import com.hostelmanagement.hostel.model.Hostel;
import com.hostelmanagement.hostel.repo.BedRepository;
import com.hostelmanagement.hostel.repo.HostelRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostelService {

    private HostelRepository hostelRepository;
    private BedRepository bedRepository;

    public HostelService(HostelRepository hostelRepository, BedRepository bedRepository) {
        this.hostelRepository = hostelRepository;
        this.bedRepository = bedRepository;
    }


    @Transactional
    public Hostel createHostel(HostelCreateDto dto) {
        Hostel hostel = new Hostel();
        hostel.setOwnerId(dto.getOwnerId());
        hostel.setName(dto.getName());
        hostel.setTotalBeds(dto.getTotalBeds());

        Hostel savedHostel = hostelRepository.save(hostel);

        for (int i = 1; i <= dto.getTotalBeds(); i++) {
            Bed bed = new Bed();
            bed.setHostelId(savedHostel.getId());
            bed.setBedNumber("BED-" + i);
            bed.setStatus(BedStatus.AVAILABLE);
            bedRepository.save(bed);
        }
        return savedHostel;

    }

    public List<Hostel> getHostelsByOwner(Long ownerId) {
        return hostelRepository.findByOwnerId(ownerId);
    }

    @Transactional
    public Hostel updateHostel(HostelUpdateDto dto ) {
        Hostel hostel = hostelRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Hostel not found"));


        // Update basic details
        if (!dto.getName().equals(hostel.getName())) {
            hostel.setName(dto.getName());
        }

        // BED COUNT UPDATE LOGIC
        if (dto.getTotalBeds() != (hostel.getTotalBeds())) {

            int newTotalBeds = dto.getTotalBeds();
            int currentTotalBeds = hostel.getTotalBeds();

            long occupiedBeds = bedRepository
                    .countByHostelIdAndStatus(hostel.getId(), String.valueOf(BedStatus.OCCUPIED));

            if (newTotalBeds < occupiedBeds) {
                throw new RuntimeException(
                        "Cannot reduce beds below occupied count: " + occupiedBeds);
            }

            // INCREASE BEDS
            if (newTotalBeds > currentTotalBeds) {
                for (int i = currentTotalBeds + 1; i <= newTotalBeds; i++) {
                    Bed bed = new Bed();
                    bed.setHostelId(hostel.getId());
                    bed.setBedNumber("BED-" + i);
                    bed.setStatus(BedStatus.AVAILABLE);
                    bedRepository.save(bed);
                }
            }

            // DECREASE BEDS
            if (newTotalBeds < currentTotalBeds) {

                int bedsToRemove = currentTotalBeds - newTotalBeds;

                List<Bed> availableBeds =
                        bedRepository.findByHostelIdAndStatus(
                                hostel.getId(), String.valueOf(BedStatus.AVAILABLE));

                if (availableBeds.size() < bedsToRemove) {
                    throw new RuntimeException(
                            "Not enough available beds to remove");
                }

                for (int i = 0; i < bedsToRemove; i++) {
                    bedRepository.delete(availableBeds.get(i));
                }
            }

            hostel.setTotalBeds(newTotalBeds);
        }

        return hostelRepository.save(hostel);
    }


}
