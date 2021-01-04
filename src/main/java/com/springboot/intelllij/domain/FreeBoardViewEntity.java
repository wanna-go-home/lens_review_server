package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "free_board_view")
@DynamicUpdate
public class FreeBoardViewEntity extends BoardBaseEntity {
    @Column(name = "nickname")
    private String nickname;
}
