package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "\"PWR\"")
public class PWREntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"pwrId\"")
    private Integer pwrId;

    @Column(name = "\"pwr\"")
    private Double pwr;

    @OneToOne
    @JoinColumn(name="\"lensId\"")
    LensEntity lensId;
}
