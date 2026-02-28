package com.hostelmanagement.hostel.modules.bed;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BedRepository extends JpaRepository<Bed, Long> {

    List<Bed> findByHostelId(Long hostelId);

    long countByHostelIdAndStatus(Long hostelId, String status);

    List<Bed> findByHostelIdAndStatus(Long hostelid, String status);

   // List<Bed> findByOwnerId(Long ownerId);
}
