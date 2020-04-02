package com.github.tsijercic1.auctionapi.response.single_product_page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SingleProductResponse {
    private SingleProduct product;
    private List<SingleBid> bids;
}
