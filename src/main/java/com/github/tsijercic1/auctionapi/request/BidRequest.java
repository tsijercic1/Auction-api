package com.github.tsijercic1.auctionapi.request;

import java.math.BigDecimal;

public class BidRequest {
    private Long bidderId;
    private Long productId;
    private BigDecimal amount;

    public BidRequest() {
    }

    public BidRequest(Long bidderId, Long productId, BigDecimal amount) {
        this.bidderId = bidderId;
        this.productId = productId;
        this.amount = amount;
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
}
