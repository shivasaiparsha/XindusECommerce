package com.example.XindusEcommerce.ServiceTest;

import com.example.XindusEcommerce.Controllers.WishListItemController;
import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Dtos.RequestDto.DeleteByIdDto;
import com.example.XindusEcommerce.Exceptions.EmptyWishListCustomException;
import com.example.XindusEcommerce.Exceptions.ItemNotFoundCustomException;
import com.example.XindusEcommerce.Exceptions.UserNotFoundException;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Models.WishListItem;
import com.example.XindusEcommerce.Repository.UserRespository;
import com.example.XindusEcommerce.Repository.WishListItemRepository;
import com.example.XindusEcommerce.Service.ItemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DeleteByItemIdTest {

    @Autowired
    private WishListItemController wishListController;


    @Autowired
    private UserRespository userRepository;


    @Autowired
    private ItemService itemService;

    @Mock
    private SecurityContext securityContextMock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);
    }


    @Autowired
    private WishListItemRepository wishListItemRepository;


    @Test
    @Transactional
    public void testDeleteItemById_validInput_success() throws Exception {
        // Given
        // Initialize mocks
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);

        // Mock authentication
//        , it returns the mock Authentication object instead of null.
        Authentication authenticationMock = mock(Authentication.class);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // Set up authentication details
        UserDetails userDetails = new User("shiva", "Shivasai12345");
        // check authentication
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);

        // Given
        Integer itemId = 1;
        String username = "shviakkksai90";
        String email = "shivasaiji12390@gmail.com";
        String password = "12347Sh004i";
        String role = "user";
        User user = new User(username, email, password, role); // Create a mock user
        userRepository.save(user);

        WishListItem wishlistItem = new WishListItem(itemId, "mobile", user);
        wishListItemRepository.save(wishlistItem);

        DeleteByIdDto deleteByIdDto = new DeleteByIdDto(itemId);

        // When
        String result = itemService.deleteItemById(deleteByIdDto);

        // Then
        // Check if item is deleted from user's wishlist
        User updatedUser = userRepository.findByEmail(email);
        assertEquals(0, updatedUser.getWishListItems().size());

        // Assert item is deleted from repository
        Optional<WishListItem> deletedItem = wishListItemRepository.findById(itemId);
        assertFalse(deletedItem.isPresent());

        assertEquals("item " + itemId + " deleted successfully", result);

        // Cleanup: delete the test user from the database
        userRepository.deleteById(user.getUserid());
    }

    @Test
    @Transactional
    public void testDeleteItemByIdUserNotFoundthrowsException() throws Exception {
        // Given
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);

        // Mock authentication
//        , it returns the mock Authentication object instead of null.
        Authentication authenticationMock = mock(Authentication.class);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // Set up authentication details
        // user not present in db
        UserDetails userDetails = new User("shiva145", "Shivasai12345");
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);

        Integer itemId = 10;
        DeleteByIdDto deleteByIdDto = new DeleteByIdDto(itemId);

          // if user not presernt in ddatabase we get null pointer exception
        assertThrows(NullPointerException.class, () -> itemService.deleteItemById(deleteByIdDto));

    }

    @Test
    @Transactional
    public void testDeleteItemById_emptyWishList_throwsException() throws Exception {
        // Given
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);

        // Mock authentication
//        , it returns the mock Authentication object instead of null.
        Authentication authenticationMock = mock(Authentication.class);

        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // Set up authentication details

        Integer userId=1;
        String username="EmptyIserWishList";
        String email = "EmptyUser@gmail.com";
        String password="12347Sh004i";
        String role="user";


        User user = new User(username, email, password, role ); // Create a mock user
        userRepository.save(user); // Save user without wishlist items
        // authenticate the mock user
        UserDetails userDetails = new User("EmptyIserWishList", "Shivasai12345");
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);

        Integer itemId=1;
        DeleteByIdDto deleteByIdDto = new DeleteByIdDto( itemId);

        // checking weather the list is empty
        assertThrows(EmptyWishListCustomException.class, () -> itemService.deleteItemById(deleteByIdDto));



        // Cleanup: delete the test user from the database
        userRepository.deleteById(user.getUserid());
    }

    // checking item not found exception
    @Test
    @Transactional
    public void testDeleteItemById_itemNotFound_throwsException() throws Exception {
        // Given
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);

        // Mock authentication
//        , it returns the mock Authentication object instead of null.
        Authentication authenticationMock = mock(Authentication.class);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // Set up authentication details
        UserDetails userDetails = new User("shiva", "Shivasai12345");
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);


        Integer userId=1;
        String username="ItemNotFounUser2";
        String email = "ItemNotFoundUser1@gmail.com";
        String password="12347Sh004i76878";
        String role="user";
        User user = new User(username, email, password, role ); // Create a user
        WishListItem item=new WishListItem(12, "shoe", user);
        userRepository.save(user);//save user
         wishListItemRepository.save(item);
        Integer itemId=30;
        DeleteByIdDto deleteByIdDto = new DeleteByIdDto(itemId); // userid useremail

        // cheking weather item present in db or not
        // expecting itemnot found exception
        assertThrows(EmptyWishListCustomException.class, () -> itemService.deleteItemById(deleteByIdDto));

        // Cleanup: delete the test user from the database
        userRepository.deleteById(user.getUserid());
    }
}
