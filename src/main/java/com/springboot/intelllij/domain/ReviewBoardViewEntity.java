package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "review_board_view")
public class ReviewBoardViewEntity extends BoardBaseEntity {
    @Column(name = "name")
    private String nickname;

    @Column(name = "lens_id")
    private Integer lensId;
}
