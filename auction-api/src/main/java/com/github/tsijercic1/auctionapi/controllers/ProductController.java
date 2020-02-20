package com.github.tsijercic1.auctionapi.controllers;

import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.exceptions.UnauthorizedException;
import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.User;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import com.github.tsijercic1.auctionapi.security.CurrentUser;
import com.github.tsijercic1.auctionapi.security.JwtTokenProvider;
import com.github.tsijercic1.auctionapi.security.UserPrincipal;
import com.github.tsijercic1.auctionapi.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    final ProductService productService;
    final UserRepository userRepository;

    public ProductController(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @GetMapping("/products")
    @PermitAll
    public ResponseEntity<Page<Product>> getAll(Pageable pageable) {
        logger.info("Accessed route products");
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @GetMapping("/{username}/products")
    @RolesAllowed("USER")
    public ResponseEntity<List<Product>> getAllForUser(@PathVariable String username, @CurrentUser UserPrincipal userPrincipal) {
        logger.info(username+" "+userPrincipal.getUsername());
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User", "username", username);
        }
        if (!user.get().getUsername().equals(userPrincipal.getUsername())) {
            throw new UnauthorizedException("Username is not yours");
        }
        return ResponseEntity.ok(productService.getAllForUser(user.get()));
    }


}
