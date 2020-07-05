package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "\"Material\"")
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"materialId\"")
    private Integer materialId;

    @Column(name = "\"material\"")
    private String material;
}
