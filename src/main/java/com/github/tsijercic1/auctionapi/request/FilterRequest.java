package com.github.tsijercic1.auctionapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterRequest {
    private String category,subcategory, search, orderBy;
    private Integer minPrice, maxPrice, page, pageSize;
    private Boolean descending;
}
