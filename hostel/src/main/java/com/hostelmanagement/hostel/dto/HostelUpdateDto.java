package com.hostelmanagement.hostel.dto;

import lombok.Data;

@Data
public class HostelUpdateDto {

    private Long id;
    private Long ownerId;
    private String name;
    private int totalBeds;
    private Double monthlyFee;
    private Double maintenanceFee;
}
