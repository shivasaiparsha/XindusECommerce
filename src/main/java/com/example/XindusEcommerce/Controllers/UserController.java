package com.example.XindusEcommerce.Controllers;


import com.example.XindusEcommerce.Dtos.RequestDto.AdduserDto;
import com.example.XindusEcommerce.Exceptions.UniqueConstraintCustomExceptions;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Service.UserService;
import jakarta.persistence.UniqueConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

      @Autowired
    private UserService userService;

      @PostMapping("/register") // user registerration
      public ResponseEntity<String> userRegistration(@RequestBody AdduserDto adduserDto) throws  Exception, UniqueConstraintCustomExceptions {

          try {
              String message = userService.addUserToDb(adduserDto); // redirect user service class
              return new ResponseEntity<>(message, HttpStatus.OK);

          } catch (UniqueConstraintCustomExceptions e){  // if user already present in fb
              return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);  // conflict between the usernames(user name exist)
          }
          catch (Exception e) { // other internal server error
              return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
          }

      }

      // before login user must be register
    @PostMapping("/Login") // user login not need to pass any parameter.selecting the basic auth for login
    public ResponseEntity<String> userlogin() throws  Exception, UniqueConstraintCustomExceptions {
          return new ResponseEntity<>("login successful", HttpStatus.OK); // Incorrect login credentials result in a 403 unauthorized error,
                                                                             // indicating a failure to authenticate.
    }
}
