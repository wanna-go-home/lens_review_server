package com.springboot.intelllij.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckUserInfoEnum {
    AVAILABLE(0),
    OCCUPIED(1),
    INVALID(2);

    private final int check_code;
}
