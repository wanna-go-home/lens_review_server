package com.springboot.intelllij.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Auth Fail")
public class AuthException extends RuntimeException{
    public AuthException(AuthExceptionEnum codeEnum){
        super(codeEnum.getMsg());
    }
}
