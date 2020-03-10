package com.github.tsijercic1.auctionapi.repositories;

import com.github.tsijercic1.auctionapi.models.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
