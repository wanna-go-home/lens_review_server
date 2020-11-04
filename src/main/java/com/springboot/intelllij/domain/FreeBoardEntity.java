package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "free_board")
public class FreeBoardEntity extends BoardBaseEntity {
}
