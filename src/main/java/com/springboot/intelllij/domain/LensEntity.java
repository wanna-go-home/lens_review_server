package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "\"Lens\"")
public class LensEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"lensId\"")
    private Integer lensId;

    @Column(name = "\"name\"", nullable = false)
    private String name;

    @Column(name = "\"graphicDia\"", nullable = true)
    private Double graphicDia;

    @Column(name = "\"perPackage\"", nullable = true)
    private Integer perPackage;

    @Column(name = "\"price\"", nullable = true)
    private Integer price;

    @Column(name = "\"reviewCnt\"", nullable = true)
    private Integer reviewCnt;

    @Column(name = "\"bc\"", nullable = true)
    private Double bc;

    @Column(name = "\"dia\"", nullable = true)
    private Double dia;

    @Column(name = "\"url\"", nullable = true)
    private String url;

    @Column(name = "\"productImage\"", nullable = true)
    private String productImage;

    @Column(name = "\"demonstrationImage\"", nullable = true)
    private String demonstrationImage;

    @Column(name = "\"per\"", nullable = true)
    private String per;


}
