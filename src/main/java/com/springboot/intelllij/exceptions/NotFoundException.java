package com.springboot.intelllij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity Not Found!")
public class NotFoundException extends RuntimeException{
    public NotFoundException(EntityNotFoundExceptionEnum codeEnum){
        super(codeEnum.getMsg());
    }
}
