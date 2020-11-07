package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "free_board_view")
public class FreeBoardViewEntity extends BoardBaseEntity {
    @Column(name = "name")
    private String nickname;
}
