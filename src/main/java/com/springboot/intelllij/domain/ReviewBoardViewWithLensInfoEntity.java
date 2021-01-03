package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewBoardViewWithLensInfoEntity extends ReviewBoardViewEntity {

    private LensEntity lensEntity;

    public ReviewBoardViewWithLensInfoEntity(ReviewBoardViewEntity reviewBoardEntity, LensEntity lensEntity) {
        super(reviewBoardEntity);
        this.lensEntity = lensEntity;
    }
}
