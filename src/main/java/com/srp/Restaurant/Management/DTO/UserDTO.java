package com.srp.Restaurant.Management.DTO;

import com.srp.Restaurant.Management.ENUMS.UserRole;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    public UserDTO(Long id, String email, String name, UserRole userRole) {
    }
}
