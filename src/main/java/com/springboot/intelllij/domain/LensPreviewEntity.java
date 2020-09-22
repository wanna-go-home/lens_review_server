package com.springboot.intelllij.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table
@Entity(name = "lens_preview")
@TypeDef(
        typeClass = JsonBinaryType.class,
        defaultForType = JsonNode.class
)
public class LensPreviewEntity {

    @Id
    @Column(name = "lens_id")
    private Integer lens_id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Integer price;

    @Column(name = "product_image", columnDefinition = "jsonb", nullable = true)
    private JsonNode productImage;
}
