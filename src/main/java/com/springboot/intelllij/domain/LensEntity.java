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

    @Column(name = "\"BC\"", nullable = true)
    private Double BC;

    @Column(name = "\"DIA\"", nullable = true)
    private Double DIA;

    @Column(name = "\"moist\"", nullable = true)
    private Integer moist;

    @Column(name = "\"name\"", nullable = true)
    private String name;

    @Column(name = "\"dk\"", nullable = true)
    private Double dk;

    @OneToOne
    @JoinColumn(name="\"materialId\"", nullable = true)
    MaterialEntity materialId;

    @OneToOne
    @JoinColumn(name="\"originId\"", nullable = true)
    OriginEntity originId;

    @OneToOne
    @JoinColumn(name="\"manId\"", nullable = true)
    ManufacturerEntity manId;

}
