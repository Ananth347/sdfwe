package com.hostelmanagement.hostel.dto;

import lombok.Data;

@Data
public class HostelCreateDto {

    private Long ownerId;
    private String name;
    private int totalBeds;
}
