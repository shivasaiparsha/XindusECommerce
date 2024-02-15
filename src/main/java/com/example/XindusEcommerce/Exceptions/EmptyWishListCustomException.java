package com.example.XindusEcommerce.Exceptions;


public class EmptyWishListCustomException extends Exception{
    public EmptyWishListCustomException(String emptyWishList) {
          super(emptyWishList);
    }
}
