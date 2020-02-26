package com.github.tsijercic1.auctionapi.payload;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class ProductRequest {
    @NotBlank
    private String name;
    private String description;
    private BigDecimal startPrice;
    private Instant auctionStart;
    private Instant auctionEnd;
    private String color;
    private String size;
    @NotBlank
    private String subcategory;


    public ProductRequest() {
    }

    public ProductRequest(String name, String description, BigDecimal startPrice, Instant auctionStart, Instant auctionEnd, String color,
                          String size, String subcategory) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.auctionStart = auctionStart;
        this.auctionEnd = auctionEnd;
        this.color = color;
        this.size = size;
        this.subcategory = subcategory;
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

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Instant getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Instant auctionEnd) {
        this.auctionEnd = auctionEnd;
    }
}
