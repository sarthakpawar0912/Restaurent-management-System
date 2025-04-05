package com.srp.Restaurant.Management.CONTROLLER.AUTH;

import com.srp.Restaurant.Management.DTO.AuthenticationRequest;
import com.srp.Restaurant.Management.DTO.AuthenticationResponse;
import com.srp.Restaurant.Management.DTO.SignUpRequest;
import com.srp.Restaurant.Management.DTO.UserDTO;
import com.srp.Restaurant.Management.ENTITIES.User;
import com.srp.Restaurant.Management.REPOSITORY.UserRepository;
import com.srp.Restaurant.Management.SERVICES.AUTH.AuthService;
import com.srp.Restaurant.Management.SERVICES.JWT.UserService;
import com.srp.Restaurant.Management.UTIL.JwtUtil;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignUpRequest signUpRequest){
        try {
            UserDTO createUser=authService.createUser(signUpRequest);
            return new ResponseEntity<>(createUser, HttpStatus.OK);
        }catch(EntityExistsException entityExistsException){
            return new ResponseEntity<>("User Already Exists",HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("User Not Created,come again later",HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())) ;
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password.");
        }

        final UserDetails userDetails=userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt= jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse=new AuthenticationResponse();
        if (optionalUser.isPresent()){
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserRole(optionalUser.get().getUserRole());
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return authenticationResponse;
    }



}
