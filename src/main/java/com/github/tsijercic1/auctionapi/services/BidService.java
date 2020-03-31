package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.repositories.BidRepository;
import com.github.tsijercic1.auctionapi.response.single_product_page.SingleBid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidService {
    private final BidRepository bidRepository;


    public BidService(final BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    public List<SingleBid> getBidsForProductByProductId(Long productId) {
        return bidRepository
                .findAllByProduct_Id(productId)
                .stream()
                .map(
                        bid ->
                                new SingleBid(
                                        bid.getBidder().getName()+" "+bid.getBidder().getSurname(),
                                        bid.getBidder().getProfilePictureUrl(),
                                        bid.getCreatedAt(),
                                        bid.getAmount()
                                )
                )
                .collect(Collectors.toList());
    }
}
