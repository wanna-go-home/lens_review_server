package com.springboot.intelllij.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionEnum {

    SMS_SERVICE_NOT_WORKING(1,"SMS Service is not working"),
    AUTH_CODE_NOT_MATCH(2,"Phone number not matched"),
    EXPIRED_REQUEST(3,"Expired Request"),
    NOT_VERIFIED(4,"Not Verified Request");

    private final int errorCode;
    private final String msg;

}
