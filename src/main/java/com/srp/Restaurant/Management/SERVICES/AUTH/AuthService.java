package com.srp.Restaurant.Management.SERVICES.AUTH;

import com.srp.Restaurant.Management.DTO.SignUpRequest;
import com.srp.Restaurant.Management.DTO.UserDTO;


public interface AuthService {

    UserDTO createUser(SignUpRequest signUpRequest);

}
