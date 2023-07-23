package dev.chiba.productservice.repo;

import dev.chiba.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product,String> {
}
