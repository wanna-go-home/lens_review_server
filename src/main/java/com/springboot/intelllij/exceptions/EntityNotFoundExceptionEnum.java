package com.springboot.intelllij.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityNotFoundExceptionEnum {

    LENS_NOT_FOUND(1,"Lens Not Found"),
    REVIEW_BOARD_NOT_FOUND(2,"Review Board Not Found"),
    USER_NOT_FOUND(3,"User Not Found"),
    POST_NOT_FOUND(4, "Post Not Found")
    ;

    private final int errorCode;
    private final String msg;

}
