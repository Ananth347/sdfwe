package com.hostelmanagement.hostel.modules.owner.dto.request;

import lombok.Data;

@Data
public class OwnerLoginDto {
    private String mobile;
    private String password;
}
