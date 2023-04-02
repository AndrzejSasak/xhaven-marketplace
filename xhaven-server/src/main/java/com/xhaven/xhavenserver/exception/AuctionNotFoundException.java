package com.xhaven.xhavenserver.exception;

public class AuctionNotFoundException extends RuntimeException {

    public AuctionNotFoundException(String message) {
        super(message);
    }
}
