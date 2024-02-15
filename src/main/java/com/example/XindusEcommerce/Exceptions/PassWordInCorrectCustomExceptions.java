package com.example.XindusEcommerce.Exceptions;

public class PassWordInCorrectCustomExceptions extends Exception{

    public PassWordInCorrectCustomExceptions(String userExist) {
        super(userExist);
    }
}
