package com.hostelmanagement.hostel.modules.owner.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OwnerUpdateDto {

    private String name;
    private String mobile;
    private String email;
    private LocalDateTime createdAt;
}
