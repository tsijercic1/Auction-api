package com.github.tsijercic1.auctionapi.response.single_product_page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleProduct {
    private Long id;
    private String name;
    private String description;
    private BigDecimal startPrice;
    private BigDecimal highestBid;
    private Integer bidNumber;
    private LocalDate auctionEnd;
    private List<SinglePicture> pictures;
}
