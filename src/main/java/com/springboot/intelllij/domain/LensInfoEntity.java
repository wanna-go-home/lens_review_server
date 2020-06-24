package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "Lens")
public class LensInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int lensId;

    private String lens;

    private float BC;

    private float DIA;

    private float PWR;

    private int waterContent;

    private String spec;

}
