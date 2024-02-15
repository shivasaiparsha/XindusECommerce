package com.example.XindusEcommerce.ServiceTest;

import com.example.XindusEcommerce.Controllers.WishListItemController;
import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Dtos.ResponseDto.UserWishListReponseDto;
import com.example.XindusEcommerce.Exceptions.EmptyWishListCustomException;
import com.example.XindusEcommerce.Exceptions.UserNotFoundException;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Models.WishListItem;
import com.example.XindusEcommerce.Repository.UserRespository;
import com.example.XindusEcommerce.Repository.WishListItemRepository;
import com.example.XindusEcommerce.Service.ItemService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
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

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemServiceClassTest {


    @Autowired
    private WishListItemController wishListController;

    @Autowired
    private UserRespository userRepository;

    @Autowired
    private ItemService itemService;

    @Mock
    private SecurityContext securityContextMock;
    @Autowired
    private WishListItemRepository wishListItemRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);
    }

//    ****************ALERT****************
    //    ******** Before performing this test user should be authenticate with mock user**********
    // else securityContextMock will give null pointer exceptions

    // authenticate user with username and password. user should be exist in db
    public  void authenticateUser(String username)
    {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);

        // Mock authentication
//        , it returns the mock Authentication object instead of null.
        Authentication authenticationMock = mock(Authentication.class);
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);

        // Set up authentication details
        UserDetails userDetails = new User(username, "Shivasai12345");
        // check authentication
        when(authenticationMock.getPrincipal()).thenReturn(userDetails);
    }

    @Test
    @Transactional
    public void  addItem_validInput_success() throws Exception {
        // try to mock with different usernames
        // user should be present in your db
        String user="XinudusTrade34";
        authenticateUser(user);

        // create dummy user
       Integer userId=1;
        String username="shviasaihjljnj90";
        String email = "Xindusshdnjkis@gmail.com";
        String password="12347Sh004ihjk";
        String role="user";
        String productname="LapTop";
        AddItemDto addItemDto = new AddItemDto( productname);
        // String username, String email, String password, String role
        User user1 = new User(username, email, password, role ); // Create a mock user
        // When
        userRepository.save(user1); // before testing your database must exist with the user
        String result = itemService.addIteam(addItemDto);

        assertEquals("item added successfully", result);
    }

    @Test
    @Transactional
    public void   addItem_NllPointerException_throwsException() throws Exception {
      // authenticate mock user with stored details in mysql
        // user with name should present in your db
        String user="XinudusTrade";
        authenticateUser(user);

        String email = "sfnknsdm@gmail.com";
        String productName = "mobile";
        AddItemDto addItemDto = new AddItemDto( productName);

        // No need to verify repository calls since exception is thrown before
        // User with the given ID does not exist, so userRepository.findById will return null
           String expectedMessage="No value present";

        NullPointerException exception= assertThrows(NullPointerException.class, () -> itemService.addIteam(addItemDto));
        Assertions.assertEquals(exception.getMessage(), expectedMessage);

    }

    @Test
    @Transactional
    public void testGetAllWishListOfUser_validItems() throws Exception {
        // Authencticacte mock user
        String username1="XinudusTrade3";
        authenticateUser(username1);

        Integer userId=1;
        String username="shviasaiXindus90";
        String email = "shivasaiXinudus0@gmail.com";
        String password="12347Sh00HjfoJKLM4i";
        String role="user";
        User user1 = new User(username, email, password, role ); // Create a dummy user
        String userEmail = "shivasai12390@gmail.com";
        userRepository.save(user1); // Save user with wishlist items
        // make sure that your item
         WishListItem wishListItem1=new WishListItem(1, "Product1", user1);
        WishListItem wishListItem2= new WishListItem(2, "Product2", user1);
        wishListItemRepository.save(wishListItem1);
        wishListItemRepository.save(wishListItem2);

        List<UserWishListReponseDto> response = itemService.getAllWishListOfUser();
          // if user not properly stored in db it gives empty list exceptions
        // Testing users product list
        assertEquals(2, response.size()); // Assert two items returned expected 2
        assertEquals(1, response.get(0).getItemId()); // item shoukd be 1
        assertEquals("Product1", response.get(0).getItemName()); // first product name should be product1
        assertEquals(2, response.get(1).getItemId());// secondeproduct id should be 2
        assertEquals("Product2", response.get(1).getItemName()); // second product name should Product2

        userRepository.deleteById(user1.getUserid());
    }

    @Test
    @Transactional
    public void testGetAllWishListOfUser_userNotFound_throwsException() throws Exception {

        String userEmail = "Xindusnonexistent@example.com"; // User doesn't exist
        authenticateUser(userEmail);

        assertThrows(UserNotFoundException.class, () -> itemService.getAllWishListOfUser());
    }

    @Test
    @Transactional
    public void testGetAllWishListOfUser_emptyWishList_throwsException() throws Exception {
        // Authenticate user
        // user with name should br present in your db
        String username1="XinudusTrade3@gmail.com";
        authenticateUser(username1);

        String username="shviasaiXindus90";
        String email = "shivasai1ttttt2390@gmail.com";
        String password="12347Sh004i";
        String role="user";
        User user = new User(username, email, password, role ); // Create a dummy user
        userRepository.save(user); // Save user without wishlist items


        // chek weather the EmptyWishListException
        assertThrows(EmptyWishListCustomException.class, () -> itemService.getAllWishListOfUser());


        // Cleanup: delete the test user from the database
        userRepository.deleteById(user.getUserid());
    }

}
