package com.example.XindusEcommerce.Repository;

import com.example.XindusEcommerce.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository  extends JpaRepository<User, Integer> {

         User findByUsername(String username);

         User findByEmail(String email);
}
