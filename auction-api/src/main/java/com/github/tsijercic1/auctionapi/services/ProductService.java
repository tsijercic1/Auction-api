package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getAllForUser(User user) {
        return productRepository.findAll().stream().filter(product ->
            product.getSeller().getId().equals(user.getId())
        ).collect(Collectors.toList());
    }
}
