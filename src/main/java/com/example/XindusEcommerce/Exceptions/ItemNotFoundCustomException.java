package com.example.XindusEcommerce.Exceptions;

public class ItemNotFoundCustomException extends Exception{
    public ItemNotFoundCustomException(String itemNotFound) {
        super(itemNotFound);
    }
}
