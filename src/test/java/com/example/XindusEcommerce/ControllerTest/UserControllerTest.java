package com.example.XindusEcommerce.ControllerTest;

import com.example.XindusEcommerce.Controllers.WishListItemController;
import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Dtos.RequestDto.AdduserDto;
import com.example.XindusEcommerce.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest{

    @Autowired
    private WishListItemController wishListItemController;
    @LocalServerPort
    private int port;

    @Autowired
    private static RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Mock
    private SecurityContext securityContextMock;

    private static String baseUrl = "http://localhost";

    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        byte[] encodedCredentials = Base64.encode(credentials.getBytes());
        return new String(encodedCredentials);
    }


    @Test
    public void testSuccessfulRegistration_Password() throws Exception {

        // checking Test case=1
        AdduserDto validDto = new AdduserDto(
                "shivasai", // Unique username
                "shivasai", // Secure password
                "shivasai134@email.com", // Valid email
                "user" // Valid role
        );
        String actual="password length should be contails atleast 12 characters";

       Throwable lengthexception = assertThrows(Exception.class, () -> {
            userService.addUserToDb(validDto);
        });
        Assertions.assertEquals(lengthexception.getMessage(), actual);

    }

    @Test
    public void testSuccessfulRegistration_PasswordUppercase() throws Exception {

        AdduserDto validDto2 = new AdduserDto(
                "shivasai", // Unique username
                "shivasai12345", // Secure password
                "shivasai134@email.com", // Valid email
                "user" // Valid role
        );
        Throwable  capsExceptions = assertThrows(Exception.class, () -> {
            userService.addUserToDb(validDto2);
        });
        String actual="password should contains atleast one Uppercase character";
        Assertions.assertEquals(capsExceptions.getMessage(), actual);

    }

    // checking weather the password contain
    @Test
    public void testSuccessfulRegistration_PasswordLowercase() throws Exception {

        AdduserDto validDto2 = new AdduserDto(
                "shivasai", // Unique username
                "SHIVASAIPARSHA12345", // Secure password
                "shivasai134@email.com", // Valid email
                "user" // Valid role
        );
        Throwable  capsExceptions = assertThrows(Exception.class, () -> {
            userService.addUserToDb(validDto2);
        });
        String actual="password should contains atleast one LowerCase character";
        Assertions.assertEquals(capsExceptions.getMessage(), actual);

    }

    @Test
    public void AdduserToDatabaseThroughUserController()
    {
        String url=baseUrl+"/register";


        // Set up headers and authentication
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodeCredentials("shiva", "Shivasai12345"));
        AdduserDto adduserDto=new AdduserDto("shivadsai12", "Shivaas123456", "shivadiapd@gmail.com", "user");
        // Create the HTTP entity with the request body and headers
        HttpEntity<AdduserDto> requestEntity = new HttpEntity<>(adduserDto, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
//
        responseEntity.getStatusCode().equals(OK);
        responseEntity.getBody().equals("Registration successful");

    }



}



