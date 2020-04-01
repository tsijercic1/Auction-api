package com.github.tsijercic1.auctionapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidRequestValueException extends RuntimeException {
    public InvalidRequestValueException() {
    }

    public InvalidRequestValueException(String message) {
        super(message);
    }
}
