package com.springboot.intelllij.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "lens")
@TypeDef(
        typeClass = JsonBinaryType.class,
        defaultForType = JsonNode.class
)
public class LensEntity extends LensBaseEntity {

    @Column(name = "per_package", nullable = true)
    private Integer perPackage;

    @Column(name = "review_cnt", nullable = true)
    private Integer reviewCnt;

    @Column(name = "bc", nullable = true)
    private Double bc;

    @Column(name = "dia", nullable = true)
    private Double dia;

    @Column(name = "url", nullable = true)
    private String url;

    @Column(name = "demonstration_image", columnDefinition = "jsonb", nullable = true)
    private JsonNode demonstrationImage;

    @Column(name = "pwr", columnDefinition = "jsonb", nullable = true)
    private JsonNode pwr;

}
