package codelook.jpa.repository;

import codelook.jpa.model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Integer> {
}
