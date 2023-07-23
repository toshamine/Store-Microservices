package dev.chiba.orderservice.service;


import dev.chiba.orderservice.dto.InventoryResponse;
import dev.chiba.orderservice.dto.OrderRequest;
import dev.chiba.orderservice.event.OrderEvent;
import dev.chiba.orderservice.model.Order;
import dev.chiba.orderservice.model.OrderItemLine;
import dev.chiba.orderservice.repo.OrderRepo;
import dev.chiba.orderservice.utility.OrderMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepo orderRepo;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderEvent> kafkaTemplate;

    @CircuitBreaker(name = "inventory",fallbackMethod = "somethingWrong")
    public String createOrder(OrderRequest orderRequest){
        Order order = OrderMapper.mapToOrder(orderRequest);

        List<String> skuCodeList = order.getOrderItemLineList()
                        .stream()
                        .map(OrderItemLine::getSkuCode)
                        .collect(Collectors.toList());

        List<InventoryResponse> inventoryResponses = webClientBuilder.build().get()
                        .uri("http://inventory-service/api/inventory",
                                uriBuilder -> uriBuilder.queryParam("skuCodeList",skuCodeList).build())
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<InventoryResponse>>(){})
                        .block();

        boolean inStock = inventoryResponses.stream()
                        .allMatch(InventoryResponse::getInStock);
        if(!inStock || inventoryResponses.size() != order.getOrderItemLineList().size()){
            throw new IllegalStateException("The items are not all in stock");
        }
        orderRepo.save(order);
        kafkaTemplate.send("notificationTopic",new OrderEvent(order.getOrderNumber()));
        return "Order Created";
    }

    public String somethingWrong(OrderRequest orderRequest,Exception exception){
        return "Sorry something went wrong!";
    }
}
