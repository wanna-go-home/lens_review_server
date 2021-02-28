package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewBoardWithLensInfoEntity extends ReviewBoardEntity {

    private LensPreviewEntity lensPreviewEntity;

    public ReviewBoardWithLensInfoEntity(ReviewBoardEntity reviewBoardEntity, LensPreviewEntity lensPreviewEntity) {
        super(reviewBoardEntity);
        this.lensPreviewEntity = lensPreviewEntity;
    }
}
