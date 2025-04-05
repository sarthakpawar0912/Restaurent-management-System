package com.srp.Restaurant.Management.SERVICES.AUTH;

import com.srp.Restaurant.Management.DTO.SignUpRequest;
import com.srp.Restaurant.Management.DTO.UserDTO;
import com.srp.Restaurant.Management.ENTITIES.User;
import com.srp.Restaurant.Management.ENUMS.UserRole;
import com.srp.Restaurant.Management.REPOSITORY.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount(){
        Optional<User> adminAccount=userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount.isEmpty()){
            User user=new User();
            user.setEmail("admin@test.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println("Admin Account created successfully");
        }else{
            System.out.println("Admin account already exist");
        }
    }

    public UserDTO createUser(SignUpRequest signUpRequest){
        if (userRepository.findFirstByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new EntityExistsException("User already present with Email: " + signUpRequest.getEmail());
        }
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUserRole(UserRole.CUSTOMER);
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createdUser = userRepository.save(user);
        return new UserDTO(
                createdUser.getId(),
                createdUser.getEmail(),
                createdUser.getName(),
                createdUser.getUserRole()
        );
    }

}
