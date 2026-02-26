package com.hostelmanagement.hostel.dto;


import lombok.Data;

@Data
public class TenantCreateDto {
    private String name;
    private String mobile;
    private String email;

    private String status; // PENDING / ACTIVE / VACATED

    private Boolean profileCompleted;
}
