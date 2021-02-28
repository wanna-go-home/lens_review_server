package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Formula;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "free_board")
@DynamicInsert
public class FreeBoardEntity extends BoardBaseEntity {
    @Formula("(SELECT a.nickname FROM account a WHERE a.id = account_id)")
    private String nickname;
}
