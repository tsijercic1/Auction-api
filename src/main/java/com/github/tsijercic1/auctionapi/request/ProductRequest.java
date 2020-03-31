package com.github.tsijercic1.auctionapi.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.Instant;

@Data
public class ProductRequest {
    @NotBlank
    private String name;
    private String description;
    private BigDecimal startPrice;
    private Instant auctionStart;
    private Instant auctionEnd;
    @NotBlank
    private String subcategory;

    public ProductRequest() {
    }

}
