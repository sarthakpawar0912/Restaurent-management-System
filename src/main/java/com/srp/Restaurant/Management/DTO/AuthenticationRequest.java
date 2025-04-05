package com.srp.Restaurant.Management.DTO;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private  String email;

    private String password;

}
