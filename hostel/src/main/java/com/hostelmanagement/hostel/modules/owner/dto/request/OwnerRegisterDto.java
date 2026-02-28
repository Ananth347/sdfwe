package com.hostelmanagement.hostel.modules.owner.dto.request;

import lombok.Data;

@Data
public class OwnerRegisterDto {

    private String name;
    private String mobile;
    private String email;
    private String password;
}
