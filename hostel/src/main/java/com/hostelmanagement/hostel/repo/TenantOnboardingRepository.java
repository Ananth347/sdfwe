package com.hostelmanagement.hostel.repo;

import com.hostelmanagement.hostel.model.TenantOnboarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantOnboardingRepository extends JpaRepository<TenantOnboarding, Long> {

    Optional<TenantOnboarding> findByToken(String token);
}
