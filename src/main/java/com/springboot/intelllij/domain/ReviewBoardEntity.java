package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "review_board")
public class ReviewBoardEntity extends BoardBaseEntity {
    @Column(name = "lens_id")
    private Integer lensId;
}
