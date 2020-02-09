package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
