package dev.chiba.orderservice.utility;

import dev.chiba.orderservice.dto.OrderItemLineRequest;
import dev.chiba.orderservice.dto.OrderRequest;
import dev.chiba.orderservice.model.Order;
import dev.chiba.orderservice.model.OrderItemLine;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order mapToOrder(OrderRequest orderRequest){
        List<OrderItemLine> orderItemLineList = orderRequest.getOrderItemLineList()
                .stream()
                .map(OrderMapper::mapToOrderItemLine)
                .collect(Collectors.toList());

        return Order.builder()
                .orderItemLineList(orderItemLineList)
                .build();
    }

    public static OrderItemLine mapToOrderItemLine(OrderItemLineRequest orderItemLineRequest){
        return OrderItemLine.builder()
                .price(orderItemLineRequest.getPrice())
                .skuCode(orderItemLineRequest.getSkuCode())
                .quantity(orderItemLineRequest.getQuantity())
                .build();
    }
}
