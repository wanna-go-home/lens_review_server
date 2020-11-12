package com.springboot.intelllij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Review Board Not Found!")
public class ReviewBoardNotFoundException extends RuntimeException {
    public ReviewBoardNotFoundException(String msg) {
        super(msg);
    }
}
