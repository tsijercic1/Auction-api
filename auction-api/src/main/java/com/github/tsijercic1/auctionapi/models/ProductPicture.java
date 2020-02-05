package com.github.tsijercic1.auctionapi.models;

import javax.persistence.*;

@Entity
@Table(name = "product_pictures")
public class ProductPicture extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @ManyToOne
    private Product product;
}
