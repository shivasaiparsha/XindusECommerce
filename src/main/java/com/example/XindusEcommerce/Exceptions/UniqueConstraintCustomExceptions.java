package com.example.XindusEcommerce.Exceptions;

public class UniqueConstraintCustomExceptions extends Exception {

    public UniqueConstraintCustomExceptions(String userExist) {
        super(userExist);
    }
}
