package com.hostelmanagement.hostel.modules.owner;


import com.hostelmanagement.hostel.modules.owner.dto.request.OwnerLoginDto;
import com.hostelmanagement.hostel.modules.owner.dto.request.OwnerRegisterDto;
import com.hostelmanagement.hostel.modules.owner.dto.request.OwnerUpdateDto;
import com.hostelmanagement.hostel.modules.tenant.TenantRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final TenantRepository tenantRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public OwnerService(OwnerRepository ownerRepository, TenantRepository tenantRepository) {
        this.ownerRepository = ownerRepository;
        this.tenantRepository = tenantRepository;
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

    public Owner updateOwner(OwnerUpdateDto dto , Long id) {

        Owner toUpdate = ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        toUpdate.setName(dto.getName());
        toUpdate.setEmail(dto.getEmail());
        toUpdate.setMobile(dto.getMobile());

        return ownerRepository.save(toUpdate);
    }

    public String forgetPassword(OwnerLoginDto dto) {
        Owner owner = ownerRepository.findByMobile(dto.getMobile())
                .orElseThrow(() -> new RuntimeException("Not Found"));

        owner.setPassword(passwordEncoder.encode(dto.getPassword()));

        return "Password Updated Successfully";
    }


}