package com.springboot.intelllij.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;
import springfox.documentation.spring.web.json.Json;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "lens")
@TypeDef(
        typeClass = JsonBinaryType.class,
        defaultForType = JsonNode.class
)
public class LensEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lens_id")
    private Integer lensId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "graphic_dia", nullable = true)
    private Double graphicDia;

    @Column(name = "per_package", nullable = true)
    private Integer perPackage;

    @Column(name = "price", nullable = true)
    private Integer price;

    @Column(name = "review_cnt", nullable = true)
    private Integer reviewCnt;

    @Column(name = "bc", nullable = true)
    private Double bc;

    @Column(name = "dia", nullable = true)
    private Double dia;

    @Column(name = "url", nullable = true)
    private String url;

    @Column(name = "product_image", columnDefinition = "jsonb", nullable = true)
    private JsonNode productImage;

    @Column(name = "demonstration_image", columnDefinition = "jsonb", nullable = true)
    private JsonNode demonstrationImage;

    @Column(name = "pwr", columnDefinition = "jsonb", nullable = true)
    private JsonNode pwr;

}
