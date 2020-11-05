package com.markorusic.webstore.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SafeModeException extends RuntimeException {
    public SafeModeException(String message) {
        super(message);
    }

    public SafeModeException() {
        super("Requested action is considered unsafe, thus it cannot be executed!");
    }
}
