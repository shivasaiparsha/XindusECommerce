package com.example.XindusEcommerce.Controllers;

import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Dtos.RequestDto.DeleteByIdDto;
import com.example.XindusEcommerce.Dtos.ResponseDto.UserWishListReponseDto;
import com.example.XindusEcommerce.Exceptions.EmptyWishListCustomException;
import com.example.XindusEcommerce.Exceptions.ItemNotFoundCustomException;
import com.example.XindusEcommerce.Exceptions.UserNotFoundException;
import com.example.XindusEcommerce.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/user")
public class WishListItemController {

    @Autowired
    private ItemService itemService;

    // add item to wishlist
     @PostMapping("/whishlistItem")
     public ResponseEntity<String> addItemToWishListItems(@RequestBody AddItemDto addItemDto) throws Exception {

           try {
               String messsage= itemService.addIteam(addItemDto);
                return new ResponseEntity<>(messsage, HttpStatus.OK);
           }catch (Exception e)
           {
               return  new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
           }
     }

     // get all  wishList items of user
     @GetMapping ("/getAllWishListItemsOfUser")
      public ResponseEntity<?> getALlWishListOfUserByUserId() throws UserNotFoundException, Exception
     {
          try {
             List<UserWishListReponseDto> userWishListDtoList=itemService.getAllWishListOfUser();
             return new ResponseEntity<>(userWishListDtoList, HttpStatus.OK);
          }
          catch (UserNotFoundException e) // if user not found with username
          {
              return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
          }
          catch (Exception e) // custome exceptions like emptylist exceptions,
          {
              return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
          }
     }

     //  delete item by itemid

     @DeleteMapping("/deleteItemById")
     public ResponseEntity<?> deleteById(@RequestBody DeleteByIdDto deleteByIdDto) throws Exception, EmptyWishListCustomException, ItemNotFoundCustomException
     {
         // user need to provide Login credentials   before accessing this end point by choosing basic auth in postman
           try {
             String message=  itemService.deleteItemById(deleteByIdDto);
              return new ResponseEntity<>(message, HttpStatus.OK);
           }
           catch (EmptyWishListCustomException e) { // if user having empty wishlist
               return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
           }
           catch (ItemNotFoundCustomException e) { // if item with item id not present in db
               return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
           }
           catch (Exception e) { // other server exceptions
               return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
           }
     }



}
