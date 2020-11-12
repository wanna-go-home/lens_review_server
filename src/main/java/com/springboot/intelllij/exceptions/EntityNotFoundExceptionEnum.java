package com.springboot.intelllij.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityNotFoundExceptionEnum {

    LENS_NOT_FOUND(1,"Lens Not Found"),
    REVIEW_BOARD_NOT_FOUND(2,"Review Board Not Found")
    ;

    private final int errorCode;
    private final String msg;

}
