package com.github.tsijercic1.auctionapi.controllers;

import com.github.tsijercic1.auctionapi.configuration.FileStorageConfiguration;
import com.github.tsijercic1.auctionapi.exceptions.InvalidRequestValueException;
import com.github.tsijercic1.auctionapi.exceptions.ResourceNotFoundException;
import com.github.tsijercic1.auctionapi.exceptions.UnauthorizedException;
import com.github.tsijercic1.auctionapi.models.*;
import com.github.tsijercic1.auctionapi.repositories.BidRepository;
import com.github.tsijercic1.auctionapi.request.BidRequest;
import com.github.tsijercic1.auctionapi.request.FilterRequest;
import com.github.tsijercic1.auctionapi.request.ProductRequest;
import com.github.tsijercic1.auctionapi.repositories.UserRepository;
import com.github.tsijercic1.auctionapi.response.BidDataResponse;
import com.github.tsijercic1.auctionapi.response.CategoryDataResponse;
import com.github.tsijercic1.auctionapi.response.ProductDataResponse;
import com.github.tsijercic1.auctionapi.response.SubcategoryData;
import com.github.tsijercic1.auctionapi.response.single_product_page.SingleBid;
import com.github.tsijercic1.auctionapi.response.single_product_page.SingleProductResponse;
import com.github.tsijercic1.auctionapi.security.CurrentUser;
import com.github.tsijercic1.auctionapi.security.UserPrincipal;
import com.github.tsijercic1.auctionapi.services.CategoryService;
import com.github.tsijercic1.auctionapi.services.FileStorageService;
import com.github.tsijercic1.auctionapi.services.ProductPictureService;
import com.github.tsijercic1.auctionapi.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final CategoryService categoryService;
    private final FileStorageConfiguration fileStorageConfiguration;
    private final ProductPictureService productPictureService;
    private final BidRepository bidRepository;



    public ProductController(final ProductService productService, final UserRepository userRepository,
                             final FileStorageService fileStorageService, final CategoryService categoryService,
                             final FileStorageConfiguration fileStorageConfiguration,
                             final ProductPictureService productPictureService,
                             final BidRepository bidRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.categoryService = categoryService;
        this.fileStorageConfiguration = fileStorageConfiguration;
        this.productPictureService = productPictureService;
        this.bidRepository = bidRepository;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<SingleProductResponse> getSingle(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getSingleProductResponse(id));
    }




    @PostMapping("/products/{id}/bids")
    @Secured("ROLE_USER")
    public ResponseEntity<List<SingleBid>> bid(@PathVariable Long id,
                                               @RequestBody BidRequest bidRequest,
                                               @CurrentUser UserPrincipal userPrincipal) {
        Optional<Product> optionalProduct = productService.get(id);
        if(!optionalProduct.isPresent()){
            throw new ResourceNotFoundException("Product", "id", id);
        }
        Product product = optionalProduct.get();

        Set<Bid> bids = product.getBids();
        if (
                bidRequest
                        .getAmount()
                        .compareTo(product.getStartPrice()) <= 0
                        ||
                        bids
                                .stream()
                                .anyMatch(bid -> {
                                    System.out.println(bid.getAmount());
                                    System.out.println(bidRequest.getAmount());
                                    System.out.println(bidRequest.getAmount().add(BigDecimal.valueOf(-0.9)));
                                    return bid.getAmount().compareTo(bidRequest.getAmount().add(BigDecimal.valueOf(-0.9))) >= 0;
                                })
        ) {
            throw new InvalidRequestValueException();
        }
        Bid bid = new Bid();
        bid.setAmount(bidRequest.getAmount());
        bid.setBidder(userRepository.findByEmail(userPrincipal.getEmail()).get());
        bid.setProduct(product);
        Bid result = bidRepository.save(bid);
        bids.add(result);
        return ResponseEntity.ok(mapToBidResponse(bids));
    }

    @GetMapping("/products/{id}/bids")
    @PermitAll
    public ResponseEntity<List<SingleBid>> getProductBids(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.get(id);
        if(!optionalProduct.isPresent()){
            throw new ResourceNotFoundException("Product", "id", id);
        }
        Product product = optionalProduct.get();
        Set<Bid> bids = product.getBids();
        List<SingleBid> result = mapToBidResponse(bids);
        return ResponseEntity.ok(result);
    }

    private List<SingleBid> mapToBidResponse(Set<Bid> bids) {
        return bids
                .stream()
                .map(
                        bid -> new SingleBid(
                                bid.getBidder().getName()+" "+bid.getBidder().getSurname(),
                                bid.getBidder().getProfilePictureUrl(),
                                bid.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDate(),
                                bid.getAmount()
                        )
                )
                .collect(Collectors.toList());
    }

    private ProductDataResponse mapToProductResponse(Product result) {
        return new ProductDataResponse(
                result.getId(),
                result.getName(),
                result.getDescription(),
                new CategoryDataResponse(
                        result.getSubcategory().getCategory().getId(),
                        result.getSubcategory().getCategory().getName(),
                        new ArrayList<>(
                                Collections
                                        .singletonList(
                                                new SubcategoryData(
                                                        result
                                                                .getSubcategory()
                                                                .getId(),
                                                        result
                                                                .getSubcategory()
                                                                .getName()
                                                )
                                        )
                        )
                ),
                result.getStartPrice(),
                result.getAuctionStart(),
                result.getAuctionEnd(),
                result.getPictures()
                        .stream()
                        .map(
                                ProductPicture::getUrl
                        )
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<ProductDataResponse>> getAll(
            @RequestParam(value = "search",required = false) String search,
            @RequestParam(value = "category", required = false) String categoryName,
            @RequestParam(value = "subcategory", required = false) String subcategoryName,
            @RequestParam(value = "minPrice", required = false) Integer minPrice,
            @RequestParam(value = "maxPrice", required = false) Integer maxPrice,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "descending", required = false) Boolean descending) {
        FilterRequest filterRequest = new FilterRequest(
                categoryName, subcategoryName, search, orderBy, minPrice, maxPrice, page, pageSize, descending
        );
        List<ProductDataResponse> result =
                ((List<Product>) productService.getAll(filterRequest))
                        .stream()
                        .map(this::mapToProductResponse)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories")
    public ResponseEntity<Iterable<CategoryDataResponse>> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryDataResponse> response = categories.stream().map(category -> {
            Collection<Subcategory> subcategories = category.getSubcategories();
            return new CategoryDataResponse(
                    category.getId(),
                    category.getName(),
                    subcategories.stream().map(
                            subcategory ->
                                    new SubcategoryData(
                                            subcategory.getId(),
                                            subcategory.getName()
                                    )
                    ).collect(Collectors.toList()));
        }).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<List<Product>> getAllForUser(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }
        return ResponseEntity.ok(productService.getAllForUser(optionalUser.get()));
    }

    @PostMapping("/{userId}/products")
    @Secured("ROLE_USER")
    public ResponseEntity<Product> create(@PathVariable Long userId,
                                          @Valid @RequestBody ProductRequest productRequest,
                                          @CurrentUser UserPrincipal userPrincipal) {
        User user = checkAuthorization(userId, userPrincipal);
        Product product = new Product();
        transformToProduct(productRequest, user, product);
        return ResponseEntity.ok(productService.create(product));
    }

    @PostMapping("/{userId}/products/{id}/images")
    @Secured("ROLE_USER")
    public ResponseEntity<Iterable<ProductPicture>> uploadPictures(
            @PathVariable("userId") Long userId,
            @PathVariable("id") Long id,
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam("pictures") MultipartFile[] multipartFiles) {
        checkAuthorization(userId, userPrincipal);
        Optional<Product> optionalProduct = productService.get(id);
        if (!optionalProduct.isPresent()) {
            throw new ResourceNotFoundException("Product", "id", id);
        }
        Product product = optionalProduct.get();
        if (!product.getSeller().getId().equals(userId)) {
            throw new UnauthorizedException("You do not own this product!");
        }
        return ResponseEntity.ok(Arrays.stream(multipartFiles).map(multipartFile ->
                productPictureService.create(multipartFile, product)
        ).collect(Collectors.toList()));
    }

    private User checkAuthorization(@PathVariable Long userId, @CurrentUser UserPrincipal userPrincipal) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("User", "Id", userId);
        }
        if (!user.get().getEmail().equals(userPrincipal.getEmail())) {
            throw new UnauthorizedException("You are not logged into your account");
        }
        return user.get();
    }

    private void transformToProduct(@RequestBody @Valid ProductRequest productRequest, User user, Product product) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStartPrice(productRequest.getStartPrice());
        product.setSubcategory(
                categoryService
                        .getSubcategoryByName(productRequest.getSubcategory())
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Subcategory",
                                        "name",
                                        productRequest.getSubcategory())));
        product.setSeller(user);
        product.setAuctionStart(productRequest.getAuctionStart());
        product.setAuctionEnd(productRequest.getAuctionEnd());
    }
}
