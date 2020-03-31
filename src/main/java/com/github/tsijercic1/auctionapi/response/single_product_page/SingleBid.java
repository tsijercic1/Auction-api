package com.github.tsijercic1.auctionapi.response.single_product_page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleBid {
    private String name;
    private String url;
    private Instant bidDate;
    private BigDecimal amount;
}
