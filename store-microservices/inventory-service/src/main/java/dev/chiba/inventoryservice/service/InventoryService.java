package dev.chiba.inventoryservice.service;


import dev.chiba.inventoryservice.dto.InventoryResponse;
import dev.chiba.inventoryservice.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    @Transactional(readOnly = true)
    public List<InventoryResponse> checkStock(List<String> skuCodeList){
        return inventoryRepo.findBySkuCodeIn(skuCodeList)
                .stream()
                .map(inventory -> InventoryResponse.builder()
                        .skuCode(inventory.getSkuCode())
                        .inStock(inventory.getQuantity() != 0)
                        .build())
                .collect(Collectors.toList());

    }
}
