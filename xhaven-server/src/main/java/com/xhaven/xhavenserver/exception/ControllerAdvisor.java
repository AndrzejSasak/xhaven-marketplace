package com.xhaven.xhavenserver.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuctionNotFoundException.class)
    public ResponseEntity<Object> handleAuctionNotFoundException(AuctionNotFoundException ex) {
        //TODO log exception
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleAuctionNotFoundException(UserAlreadyExistsException ex) {
        //TODO log exception
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuctionAlreadyFavoriteException.class)
    public ResponseEntity<Object> handleAuctionAlreadyFavoriteException(AuctionAlreadyFavoriteException ex) {
        //TODO log exception
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
