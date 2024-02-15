package com.example.XindusEcommerce.Dtos.RequestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdduserDto {

    String username;
    String password;
    String email;
    String role;
}
