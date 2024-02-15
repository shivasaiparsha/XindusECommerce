package com.example.XindusEcommerce.Dtos.RequestDto;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level= AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddItemDto {

       String itemName;

       // for the requirement i have used only one variable we can use other characteristics of product
       //like brand name, weight, price, quantity and quality
}
