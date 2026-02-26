package com.hostelmanagement.hostel.repo;

import com.hostelmanagement.hostel.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Tenant findByMobile(String number);
}
