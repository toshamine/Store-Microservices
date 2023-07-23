package dev.chiba.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chiba.productservice.dto.ProductRequest;
import dev.chiba.productservice.repo.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {


	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepo productRepo;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongo.uri",mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void createProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepo.findAll().size());
	}

	@Test
	void getProducts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	public ProductRequest getProductRequest(){
		return ProductRequest.builder()
				.name("Iphone 12")
				.description("Iphone 12")
				.price(BigDecimal.valueOf(120.000))
				.build();
	}
}
