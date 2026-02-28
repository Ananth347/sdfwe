package com.hostelmanagement.hostel.modules.owner;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findByMobile(String mobile);

    Optional<Owner> findByEmail(String email);

    boolean existsByMobile(String mobile);

   @Query(value = "select * from owner ", nativeQuery = true)
   List<Owner> getAllOwners();

}