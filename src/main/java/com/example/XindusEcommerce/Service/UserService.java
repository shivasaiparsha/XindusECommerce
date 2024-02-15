package com.example.XindusEcommerce.Service;

import com.example.XindusEcommerce.Dtos.RequestDto.AdduserDto;
import com.example.XindusEcommerce.Exceptions.UniqueConstraintCustomExceptions;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Repository.UserRespository;
import jakarta.persistence.UniqueConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRespository userRespository;

     private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();



    public String addUserToDb(AdduserDto adduserDto) throws Exception,UniqueConstraintCustomExceptions {

              // checking user with name already exist or not
        // if user exist throwing usernot found exception
        if(userRespository.findByUsername(adduserDto.getUsername())!=null) throw new Exception("user name "+adduserDto.getUsername()+" already exist");

        if(userRespository.findByEmail(adduserDto.getEmail())!=null) throw new UniqueConstraintCustomExceptions("user email already exist");

         try {

             IsValidPassword(adduserDto.getPassword());// check weather entered password is vallid or not
             String encodedPassword= passwordEncoder.encode(adduserDto.getPassword()); // encode the password for more securoty
             User user = new User(adduserDto.getUsername(),adduserDto.getEmail(), encodedPassword,adduserDto.getRole());
             userRespository.save(user);
             return "Registration successful";
         }
         catch (Exception e) {
             throw new Exception(e.getMessage());
         }

    }
     boolean IsValidPassword(String password) throws Exception{
              if(password.length()<12) throw new Exception("password length should be contails atleast 12 characters");
              if(password.length()>32) throw new Exception("password length should be maimum of 32 characters");
             boolean isDigitsPresents=false; // check digits presents in the passowrd
             boolean isLowerCasePresent=false;//check lower case letters present in password
             boolean isUpperCasePresent=false; // check uppercase letter present in password
             for(Character c : password.toCharArray())
             {
                  if(c>='0'&&c<='9') isDigitsPresents=true; // if numbers present
                  if(c>='A'&&c<='Z') isUpperCasePresent=true; // if Uppercase letter presents
                  if(c>='a'&&c<='z') isLowerCasePresent=true; // if lowercase letter presents
                  if(isDigitsPresents&&isUpperCasePresent&&isLowerCasePresent) break;// for average case scenario it takes less time
             }
             // throwing exceptions according to characters presents
             if(!isDigitsPresents) throw new Exception("password should contains atleast one numeric");
             if(!isUpperCasePresent) throw new Exception("password should contains atleast one Uppercase character");
             if(!isLowerCasePresent) throw new Exception("password should contains atleast one LowerCase character");
           return true;
     }
}
