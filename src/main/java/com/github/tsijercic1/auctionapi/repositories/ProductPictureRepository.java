package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
    List<ProductPicture> findAllByProduct_Id(Long id);
}
