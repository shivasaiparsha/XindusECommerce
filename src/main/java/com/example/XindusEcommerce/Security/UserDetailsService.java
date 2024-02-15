package com.example.XindusEcommerce.Security;

import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRespository userRespository;
    // once the user enter credentials this method will invoke and check weather the user presents or not
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          UserDetails user=userRespository.findByUsername(username);
        if(user==null)  throw new UsernameNotFoundException("user not found exception");
        return user;
    }


}
