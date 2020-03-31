package com.github.tsijercic1.auctionapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidRequest {
    private Long bidderId;
    private Long productId;
    private BigDecimal amount;
}
