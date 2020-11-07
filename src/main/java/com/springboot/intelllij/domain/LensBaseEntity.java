package com.springboot.intelllij.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@MappedSuperclass
@Getter
@Setter
@TypeDef(
        typeClass = JsonBinaryType.class,
        defaultForType = JsonNode.class
)
public class LensBaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lens_id")
    private Integer lensId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "graphic_dia")
    private Float graphicDia;

    @Column(name = "product_image", columnDefinition = "jsonb", nullable = true)
    private JsonNode productImage;
}
