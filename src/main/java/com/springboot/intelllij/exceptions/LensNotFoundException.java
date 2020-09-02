package com.springboot.intelllij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Lens Not Found!")
public class LensNotFoundException extends RuntimeException {
    public LensNotFoundException(String msg) {
        super(msg);
    }
}
