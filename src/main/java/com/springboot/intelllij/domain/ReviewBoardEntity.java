package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review_board")
@DynamicUpdate
public class ReviewBoardEntity extends BoardBaseEntity {
    @Column(name = "lens_id")
    private Integer lensId;

    public ReviewBoardEntity(ReviewBoardDto reviewBoardDto, Integer accountId) {
        super(accountId, reviewBoardDto.getTitle(), reviewBoardDto.getContent());
        this.lensId = reviewBoardDto.getLensId();
    }
}
