package com.example.XindusEcommerce.Transforms;

import com.example.XindusEcommerce.Dtos.RequestDto.AddItemDto;
import com.example.XindusEcommerce.Models.User;
import com.example.XindusEcommerce.Models.WishListItem;
import lombok.Builder;

@Builder
public class ItemTransforms {
   // this method is used to build wishlist item
     public static WishListItem createWhisListItem(AddItemDto addItemDto, User user)
    {
                    WishListItem wishListItem= WishListItem.builder()
                            .user(user)
                            .itemName(addItemDto.getItemName())
                            .build() ;
                    return wishListItem;
    }
}
