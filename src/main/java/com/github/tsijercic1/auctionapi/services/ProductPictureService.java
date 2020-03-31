package com.github.tsijercic1.auctionapi.services;

import com.github.tsijercic1.auctionapi.models.Product;
import com.github.tsijercic1.auctionapi.models.ProductPicture;
import com.github.tsijercic1.auctionapi.repositories.ProductPictureRepository;
import com.github.tsijercic1.auctionapi.response.single_product_page.SinglePicture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductPictureService {
    final ProductPictureRepository productPictureRepository;
    final FileStorageService fileStorageService;

    public ProductPictureService(ProductPictureRepository productPictureRepository, FileStorageService fileStorageService) {
        this.productPictureRepository = productPictureRepository;
        this.fileStorageService = fileStorageService;
    }

    public ProductPicture create(MultipartFile multiPartFile, Product product) {
        String url = fileStorageService.storeFile(multiPartFile, product.getId(), product.getSeller().getId());
        ProductPicture productPicture = new ProductPicture();
        productPicture.setUrl(url);
        productPicture.setProduct(product);
        return productPictureRepository.save(productPicture);
    }

    public List<SinglePicture> getPicturesForProductByProductId(Long id) {
        return productPictureRepository
                .findAllByProduct_Id(id)
                .stream()
                .map(productPicture -> new SinglePicture(productPicture.getUrl()))
                .collect(Collectors.toList());
    }
}
