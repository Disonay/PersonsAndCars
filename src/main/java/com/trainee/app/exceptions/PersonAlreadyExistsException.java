package com.trainee.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PersonAlreadyExistsException extends RuntimeException{
    public PersonAlreadyExistsException() {
        super();
    }
}
