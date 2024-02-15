package com.example.XindusEcommerce.RepositoryTest;

import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Models.WishListItem;
import com.example.XindusEcommerce.Repository.UserRespository;
import com.example.XindusEcommerce.Repository.WishListItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static jdk.dynalink.linker.support.Guards.isNotNull;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WishListRepoTesting {
    @Mock
    private SecurityContext securityContextMock;

    @Autowired
    private UserRespository userRespository;
    @Autowired
    private WishListItemRepository wishListItemRepository;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SecurityContextHolder.setContext(securityContextMock);
    }
    private TestEntityManager entityManager;

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
    public void testFindAllItemsOfUser() {
        //authenticate user to find all his product
        String username="XinudusTrade1"; // make sure user should be exist in db
        authenticateUser(username);
        User user=userRespository.findByUsername(username);

        WishListItem item1 = new WishListItem();
        item1.setItemName("mobile");
        item1.setUser(user);
        user.getWishListItems().add(item1);

        // ... set item properties
        wishListItemRepository.save(item1);

        WishListItem item2 = new WishListItem();
        item2.setUser(user);
        item2.setItemName("pen");
        userRespository.save(user);

        // ... set item properties
        wishListItemRepository.save(item2);

        List<WishListItem> items = wishListItemRepository.findAll();// find the all items in the data base
        Assertions.assertEquals(items.size(), 2);
        User user1=userRespository.findByUsername("xindus1234rfv");//find the items of  particular user
        Assertions.assertEquals(user1.getWishListItems().size(), 0);
        User user2=userRespository.findByUsername(username);//find the items of  particular user
        Assertions.assertEquals(user2.getWishListItems().size(), 2);
    }

    @Test
    public void testFindById() {
       int itemId=1;
       String expectedusername="Shivasai23";//user with name must be exist in db
        WishListItem foundItem = wishListItemRepository.findById(itemId).orElse(null);
        Assertions.assertEquals(foundItem.getUser().getUsername(), expectedusername);
    }
    @Test
    public void testSave() {
        WishListItem item = new WishListItem();
        // ... set item properties
        item.setItemName("MacBookPro");

        WishListItem savedItem = wishListItemRepository.save(item);

        Assertions.assertEquals(savedItem.getItemId(), isNotNull()); // Ensure ID is generated
        Assertions.assertEquals(wishListItemRepository.findById(savedItem.getItemId()), savedItem);
    }



}
