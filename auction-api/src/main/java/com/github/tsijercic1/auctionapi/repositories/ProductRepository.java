package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
