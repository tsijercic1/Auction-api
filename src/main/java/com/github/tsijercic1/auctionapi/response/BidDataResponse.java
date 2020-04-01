package com.github.tsijercic1.auctionapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidDataResponse {
    private Long id;
    private Long bidderId;
    private Long productId;
    private BigDecimal amount;
    private LocalDate bidTime;
}
