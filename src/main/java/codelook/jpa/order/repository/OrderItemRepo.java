package codelook.jpa.order.repository;

import codelook.jpa.order.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
