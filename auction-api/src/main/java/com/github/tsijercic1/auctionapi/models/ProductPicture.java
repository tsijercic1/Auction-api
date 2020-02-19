package com.github.tsijercic1.auctionapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "product_pictures")
public class ProductPicture extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String url;
    @ManyToOne
    private Product product;
}
