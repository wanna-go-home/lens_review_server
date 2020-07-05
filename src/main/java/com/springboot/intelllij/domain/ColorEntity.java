package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "\"Color\"")
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"colorId\"")
    private Integer colorId;

    @Column(name = "\"color\"")
    private String color;

    @OneToOne
    @JoinColumn(name="\"lensId\"")
    LensEntity lensId;
}
