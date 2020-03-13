package com.github.tsijercic1.auctionapi.response;

import java.math.BigDecimal;
import java.time.Instant;

public class BidDataResponse {
    private Long id;
    private Long bidderId;
    private Long productId;
    private BigDecimal amount;
    private Instant bidTime;

    public BidDataResponse() {
    }

    public BidDataResponse(Long id, Long bidderId, Long productId, BigDecimal amount, Instant bidTime) {
        this.id = id;
        this.bidderId = bidderId;
        this.productId = productId;
        this.amount = amount;
        this.bidTime = bidTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBidderId() {
        return bidderId;
    }

    public void setBidderId(Long bidderId) {
        this.bidderId = bidderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getBidTime() {
        return bidTime;
    }

    public void setBidTime(Instant bidTime) {
        this.bidTime = bidTime;
    }
}
