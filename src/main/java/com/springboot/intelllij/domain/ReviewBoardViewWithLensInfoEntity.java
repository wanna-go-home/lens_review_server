package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewBoardViewWithLensInfoEntity extends ReviewBoardViewEntity {

    private LensPreviewEntity lensPreviewEntity;

    public ReviewBoardViewWithLensInfoEntity(ReviewBoardViewEntity reviewBoardEntity, LensPreviewEntity lensPreviewEntity) {
        super(reviewBoardEntity);
        this.lensPreviewEntity = lensPreviewEntity;
    }
}
