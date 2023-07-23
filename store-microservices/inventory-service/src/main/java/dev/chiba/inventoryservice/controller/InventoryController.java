package dev.chiba.inventoryservice.controller;

import dev.chiba.inventoryservice.dto.InventoryResponse;
import dev.chiba.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> checkStock(@RequestParam List<String> skuCodeList){
        return inventoryService.checkStock(skuCodeList);
    }
}
