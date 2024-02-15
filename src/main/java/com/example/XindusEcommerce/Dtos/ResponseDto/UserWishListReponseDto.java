package com.example.XindusEcommerce.Dtos.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWishListReponseDto {

    private Integer itemId;
    private String itemName;

}
