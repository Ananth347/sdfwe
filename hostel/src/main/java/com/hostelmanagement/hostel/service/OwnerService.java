package com.hostelmanagement.hostel.service;


import com.hostelmanagement.hostel.dto.OwnerLoginDto;
import com.hostelmanagement.hostel.dto.OwnerRegisterDto;
import com.hostelmanagement.hostel.model.Owner;
import com.hostelmanagement.hostel.repo.OwnerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner registerOwner(OwnerRegisterDto request) {

        if (ownerRepository.existsByMobile(request.getMobile())) {
            throw new RuntimeException("Mobile number already registered");
        }

        Owner owner = new Owner();
        owner.setName(request.getName());
        owner.setMobile(request.getMobile());
        owner.setEmail(request.getEmail());
        owner.setPassword(passwordEncoder.encode(request.getPassword()));

        return ownerRepository.save(owner);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.getAllOwners();
    }

    public OwnerLoginDto ownerLogin(OwnerLoginDto ownerLoginDto) {
        Owner owner = ownerRepository.findByMobile(ownerLoginDto.getMobile())
                .orElseThrow(() -> new RuntimeException("Invalid mobile number"));

        if (!passwordEncoder.matches(ownerLoginDto.getPassword(), owner.getPassword())) {
            throw new RuntimeException("Invalid password");
        }


        OwnerLoginDto response = new OwnerLoginDto();
        response.setPassword(null);

        return response;
    }
}