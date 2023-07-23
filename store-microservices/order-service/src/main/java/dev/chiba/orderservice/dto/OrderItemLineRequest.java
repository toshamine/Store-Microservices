package dev.chiba.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderItemLineRequest {

    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
