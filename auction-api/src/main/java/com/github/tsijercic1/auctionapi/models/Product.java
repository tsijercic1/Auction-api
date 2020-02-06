package com.github.tsijercic1.auctionapi.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private BigDecimal startPrice;
    @NotBlank
    private Instant auctionStart;
    @NotBlank
    private Instant auctionEnd;
    private String color;
    private String size;

    @ManyToOne
    private Subcategory subcategory;

    @ManyToOne
    private User seller;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private Set<ProductPicture> pictures = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private Set<Bid> bids = new HashSet<>( );

    public Product() {
    }

    public Product(String name, String description, BigDecimal startPrice, Instant auctionStart,
                   Instant auctionEnd, String color, String size) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.color = color;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public Instant getAuctionStart() {
        return auctionStart;
    }

    public void setAuctionStart(Instant auctionStart) {
        this.auctionStart = auctionStart;
    }

    public Instant getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Instant auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Set<ProductPicture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<ProductPicture> pictures) {
        this.pictures = pictures;
    }

    public Set<Bid> getBids() {
        return bids;
    }

    public void setBids(Set<Bid> bids) {
        this.bids = bids;
    }
}
