package com.ceos18.springboot.database;

import com.ceos18.springboot.database.base.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity(name = "PROD_IMG_LIST")
public class ProductImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROD_IMG_NO")
    private Long productImageNo;

    @ManyToOne
    @JoinColumn(name = "PROD_NO")
    private Product productNo;

    @Column(name = "IMG_URL", length = 200)
    @Size(max = 200)
    private String productImageUrl;
}
