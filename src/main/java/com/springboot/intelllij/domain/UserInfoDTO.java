package com.springboot.intelllij.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private int reviewCount;
    private int freeCount;
    private int reviewCommentCount;
    private int freeCommentCount;
    private int likeCount;
}
