package com.example.XindusEcommerce.ControllerTest;

import com.example.XindusEcommerce.Controllers.WishListItemController;
import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Dtos.RequestDto.DeleteByIdDto;
import com.example.XindusEcommerce.Exceptions.ItemNotFoundCustomException;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Models.WishListItem;
import com.example.XindusEcommerce.Repository.WishListItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)// set the random port to access the classes
class ItemControllerTest {

    @Autowired
    private WishListItemController wishListItemController;


    @Mock
    private SecurityContext securityContextMock;
    @LocalServerPort
    private int port;

    private static String baseUrl = "http://localhost";

    @Autowired
    private WishListItemRepository wishListItemRepository;

    @Autowired
    private static RestTemplate restTemplate;

    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void createUri(){
        baseUrl = baseUrl.concat(":").concat(String.valueOf(port));
    }



    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        byte[] encodedCredentials = Base64.encode(credentials.getBytes());
        return new String(encodedCredentials);
    }


    @Test
    public void AddItemToDatabaseThrowWishListController()
    {
       String url=baseUrl+"/user/whishlistItem";


        // Set up headers and authentication
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodeCredentials("shiva", "Shivasai12345"));
          AddItemDto addItemDto=new AddItemDto("Light");
        // Create the HTTP entity with the request body and headers
        HttpEntity<AddItemDto> requestEntity = new HttpEntity<>(addItemDto, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        responseEntity.getStatusCode().equals(OK);
        responseEntity.getBody().equals("item added successfully");

    }

    @Test
    public void  getItemsListOfUser()
    {
        String url=baseUrl+"/user/getAllWishListItemsOfUser";
        String username="'shiva";
        String password="Shivasai12345";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodeCredentials("shiva", "Shivasai12345"));
        List<WishListItem> wishListItemList=wishListItemRepository.findAll();// no of whishList presents in db
       Assertions.assertEquals(4, wishListItemList.size()); // assert that total no of wishListItemList

        // Create the HTTP entity with the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        List<WishListItem> wishListItemList2=wishListItemRepository.findAll();
        Assertions.assertEquals(3, wishListItemList2.size());
        responseEntity.getStatusCode().equals(OK);

    }

    // delete method
    @Test
    public void  Delete_By_ItemId()
    {
        String url=baseUrl+"/user/deleteItemById";
        String username="'shiva";
        String password="Shivasai12345";
        HttpHeaders headers = new HttpHeaders(); // create headers
        headers.set("Content-Type", "application/json");// set the
        headers.set("Authorization", "Basic " + encodeCredentials("shiva", "Shivasai12345"));// encode the string with Base26
        DeleteByIdDto deleteByIdDto=new DeleteByIdDto(1);//make sure that item should be present in the db.else it throws itemNOFoundException
        // Create the HTTP entity with the request body and headers
        HttpEntity<DeleteByIdDto> requestEntity = new HttpEntity<>(deleteByIdDto, headers); // create response entity

        //send the request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        responseEntity.getStatusCode().equals(OK);
        responseEntity.getBody().equals("item  deleted successfully");

    }

    @Test
    public void  Delete_By_ItemId_ItemNotFoundException() throws Exception
    {
        String url=baseUrl+"/user/deleteItemById";
        String username="'shiva";
        String password="Shivasai12345";
        HttpHeaders headers = new HttpHeaders(); // create headers
        headers.set("Content-Type", "application/json");// set the
        headers.set("Authorization", "Basic " + encodeCredentials("shiva", "Shivasai12345"));// encode the string with Base26

        DeleteByIdDto deleteByIdDto=new DeleteByIdDto(100);//make sure that item should be present in the db.else it throws itemNOFoundException
        // Create the HTTP entity with the request body and headers
        HttpEntity<DeleteByIdDto> requestEntity = new HttpEntity<>(deleteByIdDto, headers); // create response entity

        //send the request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, String.class);
        responseEntity.getStatusCode().equals(404);
        responseEntity.getBody().equals("item not found exceptions");

    }









}

