package dev.chiba.productservice.utility;

import dev.chiba.productservice.dto.ProductRequest;
import dev.chiba.productservice.dto.ProductResponse;
import dev.chiba.productservice.model.Product;

public class ProductMapper {

    public static Product mapToProduct(ProductRequest productRequest){
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }

    public static ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
