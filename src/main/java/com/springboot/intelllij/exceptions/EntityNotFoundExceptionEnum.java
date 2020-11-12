package com.springboot.intelllij.exceptions;

public enum EntityNotFoundExceptionEnum {

    LENS_NOT_FOUND(1,"Lens Not Found"),
    REVIEW_BOARD_NOT_FOUND(2,"Review Board Not Found")
    ;

    private final int errorCode;
    private final String msg;

    public int getErrorCode(){
        return errorCode;
    }
    public String getMsg(){
        return msg;
    }

    EntityNotFoundExceptionEnum(final int errorCode, final String msg){
        this.errorCode = errorCode;
        this.msg = msg;
    }
}
