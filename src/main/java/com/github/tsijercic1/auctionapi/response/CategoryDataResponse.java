package com.github.tsijercic1.auctionapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDataResponse {
    private Long id;
    private String name;
    private List<SubcategoryData> subcategories;
}
