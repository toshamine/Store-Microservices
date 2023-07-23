package dev.chiba.productservice.service;


import dev.chiba.productservice.dto.ProductRequest;
import dev.chiba.productservice.dto.ProductResponse;
import dev.chiba.productservice.model.Product;
import dev.chiba.productservice.repo.ProductRepo;
import dev.chiba.productservice.utility.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;


    public void createProduct(ProductRequest productRequest){
        Product product = ProductMapper.mapToProduct(productRequest);
        productRepo.save(product);
        log.info("Product {} saved !",product.getName());
    }


    public List<ProductResponse> getAll(){
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(ProductMapper::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
