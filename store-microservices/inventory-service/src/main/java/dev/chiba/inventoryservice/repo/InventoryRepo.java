package dev.chiba.inventoryservice.repo;

import dev.chiba.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepo extends JpaRepository<Inventory,Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCodeList);
}
