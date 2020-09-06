package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FreeBoardPreview {
    private Integer id;
    private Integer user_id;
    private String title;
    private String preview;
}
