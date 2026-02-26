package com.hostelmanagement.hostel.dto;

import lombok.Data;

@Data
public class BedUpdateDto {

    private Long id;
    private String bedName;
    private Long hostelId;

    private Double monthlyFee;
    private Double maintenanceFee;
}
