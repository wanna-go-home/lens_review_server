package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "\"LensImage\"")
public class LensImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"imageId\"")
    private Integer imageId;

    @Column(name = "\"imagePath\"")
    private String imagePath;

    @Column(name = "\"imageName\"")
    private String imageName;

    @Column(name = "\"imageExt\"")
    private String imageExt;

    @Column(name = "\"imageSize\"")
    private Integer imageSize;

    @Column(name = "\"imageType\"")
    private Integer imageType;

    @OneToOne
    @JoinColumn(name="\"lensId\"")
    LensEntity lensId;
}
