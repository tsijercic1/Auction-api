package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.request.FilterRequest;

import java.util.List;

public interface ProductRepositoryInterface {
    List<Product> findByFilter(FilterRequest filterRequest);
}
