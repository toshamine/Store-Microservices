package dev.chiba.orderservice.repo;

import dev.chiba.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
