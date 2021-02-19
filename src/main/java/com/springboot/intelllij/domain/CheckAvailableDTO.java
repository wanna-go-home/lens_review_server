package com.springboot.intelllij.domain;

import lombok.Getter;

@Getter
public class CheckAvailableDTO {
    boolean isAvailable;
    int checkCode;

    public CheckAvailableDTO(boolean isAvailable, int checkCode) {
        this.isAvailable = isAvailable;
        this.checkCode = checkCode;

    }
}
