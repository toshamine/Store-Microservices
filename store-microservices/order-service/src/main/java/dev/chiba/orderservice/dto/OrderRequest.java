package dev.chiba.orderservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderRequest {

    private Long id;
    private String orderNumber;
    private List<OrderItemLineRequest> orderItemLineList;

}
