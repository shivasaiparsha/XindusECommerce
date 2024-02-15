package com.example.XindusEcommerce.Service;

import com.example.XindusEcommerce.Dtos.RequestDto.DeleteByIdDto;
import com.example.XindusEcommerce.Dtos.ResponseDto.UserWishListReponseDto;
import com.example.XindusEcommerce.Exceptions.EmptyWishListCustomException;
import com.example.XindusEcommerce.Exceptions.ItemNotFoundCustomException;
import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Exceptions.UserNotFoundException;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Models.WishListItem;
import com.example.XindusEcommerce.Repository.UserRespository;
import com.example.XindusEcommerce.Repository.WishListItemRepository;
import com.example.XindusEcommerce.Security.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.example.XindusEcommerce.Transforms.ItemTransforms.createWhisListItem;

@Service
public class ItemService {
    @Autowired
    private UserRespository userRespository;

    @Autowired
    private WishListItemRepository wishListItemRepository;

    @Autowired
    private UserDetailsService userDetailsService;
    public String addIteam(AddItemDto addItemDto) throws Exception,NoSuchElementException  {
        // once authentication and authorization succesfull user can able to access his whishlist
        // Retrieve username from SecurityContext
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

          // Now you can use the username to call the loadUserByUsername method
        User user = userRespository.findByUsername(username);


          WishListItem whislistItem= createWhisListItem(addItemDto, user); // create the item wishlist
               wishListItemRepository.save(whislistItem); // save wish list in repository
               user.getWishListItems().add(whislistItem);// save item in user wishlist also
               userRespository.save(user);
        return "item added successfully";
    }
    public List<UserWishListReponseDto> getAllWishListOfUser() throws NoSuchElementException,UserNotFoundException,Exception
    {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Now we can use the username to call the loadUserByUsername method
        User user = userRespository.findByUsername(username);
         if(user==null) throw new NoSuchElementException("user not found");



         // if user not have items in his wishlist it throws empty list
          if(user.getWishListItems().size()==0) throw new EmptyWishListCustomException("Empty WishList");

              List<UserWishListReponseDto> userWishListReponseDtos=new ArrayList<>();
              for(WishListItem wishListItem : user.getWishListItems())
              {
                  // creating object with item id and item name
                   userWishListReponseDtos.add(new UserWishListReponseDto(wishListItem.getItemId(), wishListItem.getItemName()));

              }
              // returning userWishListReponseDtos
              return userWishListReponseDtos;
    }

    // delete item by item id
    public String deleteItemById(DeleteByIdDto deleteByIdDto) throws ItemNotFoundCustomException,Exception,EmptyWishListCustomException
    {
        // after successfull login user can have access to his whishlist


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        // Now we can use the username to call the loadUserByUsername method
           User user = userRespository.findByUsername(username);
           //base case if user has empty list through exception
             if(user.getWishListItems().size()==0) throw new EmptyWishListCustomException("empty list"); // whishlist size=0

        // get the userwishlist
             List<WishListItem> userWishlist=user.getWishListItems();
           // In the user wishlist items are stored in sorted order(increasing order)
             // we can implement binary search algorithm to delete item.it Takes Log(userWishlist)

               int startIndex=0;    // we were searching on the list of items  starts from 0 to end list.size-1
               int endIndex=userWishlist.size()-1;
               boolean checkItemDeletedOrNot=false;
               while (startIndex<=endIndex)
               {
                    int mid= startIndex+(endIndex-startIndex)/2; // get the mid index
                      if(userWishlist.get(mid).getItemId()==deleteByIdDto.getItemId()) { // when the mid index item id equal to require item it
                           userWishlist.remove(mid);//remove the item by index
                          checkItemDeletedOrNot=true; // make variable true means item found
                          break;
                      }
                      else if(userWishlist.get(mid).getItemId()>deleteByIdDto.getItemId())
                      {
                          endIndex=mid-1; // if mid index itemid greater than require itemid item will present in left side
                      }
                      else startIndex=mid+1; // if mid index itemid less than required item id then item will present in rightside
               }
//               take O(logn) time complexity to delete item with id
               // if item not found through
               if(checkItemDeletedOrNot==false) throw new ItemNotFoundCustomException("item not found exceptions");
                   userRespository.save(user);// save user
              wishListItemRepository.deleteById(deleteByIdDto.getItemId());// delete item in wishlist repo
              return "item  deleted successfully";
     }
}
