package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.models.Category;
import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.Subcategory;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.repositories.ProductRepository;
import com.github.tsijercic1.auctionapi.response.single_product_page.SingleBid;
import com.github.tsijercic1.auctionapi.response.single_product_page.SingleProduct;
import com.github.tsijercic1.auctionapi.response.single_product_page.SingleProductResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final BidService bidService;
    private final ProductPictureService productPictureService;

    public ProductService(final ProductRepository productRepository,
                          final CategoryService categoryService,
                          final BidService bidService,
                          final ProductPictureService productPictureService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.bidService = bidService;
        this.productPictureService = productPictureService;
    }

    public Product get(Long id) {
        return productRepository.getOne(id);
    }


    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product update(Long id, Product product) {
        if (get(id) == null) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public Iterable<Product> getAll(String categoryName, String subcategoryName, String search) {
        if (search != null) {
            return productRepository
                    .findAll()
                    .stream()
                    .filter(
                            product ->
                                    product
                                            .getName()
                                            .contains(search)
                    )
                    .collect(Collectors.toList());
        }
        if (subcategoryName != null && categoryName != null) {
            List<Category> categories = categoryService
                    .getCategories()
                    .stream()
                    .filter(category ->
                            category
                                    .getName()
                                    .equals(categoryName))
                    .collect(Collectors.toList());
            if (categories.size() != 0) {
                List<Subcategory> subcategories = categories
                        .get(0)
                        .getSubcategories()
                        .stream()
                        .filter(subcategory -> subcategory.getName().equals(subcategoryName))
                        .collect(Collectors.toList());
                if (subcategories.size() != 0) {
                    return productRepository.findAllBySubcategory(subcategories.get(0));
                }
            }
            return new ArrayList<>();
        } else if (categoryName != null) {
            return productRepository
                    .findAll()
                    .stream()
                    .filter(
                            product ->
                                    product
                                            .getSubcategory()
                                            .getCategory()
                                            .getName()
                                            .equals(categoryName)
                    )
                    .collect(Collectors.toList());
        }
        return productRepository.findAll();
    }

    public List<Product> getAllForUser(User user) {
        return productRepository.findAll().stream().filter(product ->
            product.getSeller().getId().equals(user.getId())
        ).collect(Collectors.toList());
    }

    public SingleProductResponse getSingleProductResponse(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        Product product = optionalProduct.get();
        List<SingleBid> bids = bidService.getBidsForProductByProductId(product.getId());
        return new SingleProductResponse(
                new SingleProduct(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getStartPrice(),
                        bids.stream().reduce(new SingleBid("","", Instant.now(),new BigDecimal(0)),(a, b)->a.getAmount().compareTo(b.getAmount())>0?a:b).getAmount(),
                        bids.size(),
                        product.getAuctionEnd(),
                        productPictureService.getPicturesForProductByProductId(product.getId())
                ),
                bids
        );
    }
}
