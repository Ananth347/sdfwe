package com.hostelmanagement.hostel.repo;

import com.hostelmanagement.hostel.model.Hostel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostelRepository extends JpaRepository<Hostel, Long> {

    List<Hostel> findByOwnerId(Long ownerId);
}
