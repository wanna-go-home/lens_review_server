package com.springboot.intelllij.domain;

import lombok.Getter;

@Getter
public class CheckAvailableDTO {
    boolean isAvailable;
    int check_code;

    public CheckAvailableDTO(boolean isAvailable, int check_code) {
        this.isAvailable = isAvailable;
        this.check_code = check_code;
    }
}
