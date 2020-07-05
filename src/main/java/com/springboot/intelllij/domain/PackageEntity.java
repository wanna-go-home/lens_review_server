package com.springboot.intelllij.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "\"Package\"")
public class PackageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "\"packageId\"")
    private Integer packageId;

    @Column(name = "\"packageName\"")
    private String packageName;

    @Column(name = "\"perPackage\"")
    private Integer perPackage;

    @Column(name = "\"price\"")
    private Integer price;

    @OneToOne
    @JoinColumn(name="\"lensId\"")
    LensEntity lensId;
}
