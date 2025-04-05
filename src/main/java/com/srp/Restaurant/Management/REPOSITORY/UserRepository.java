package com.srp.Restaurant.Management.REPOSITORY;

import com.srp.Restaurant.Management.ENTITIES.User;
import com.srp.Restaurant.Management.ENUMS.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findFirstByEmail(String email);

    Optional<User> findByUserRole(UserRole userRole);

}
