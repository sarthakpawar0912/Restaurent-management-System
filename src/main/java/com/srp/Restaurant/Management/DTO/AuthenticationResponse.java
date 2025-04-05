package com.srp.Restaurant.Management.DTO;

import com.srp.Restaurant.Management.ENUMS.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;
}
