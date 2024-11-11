package codelook.jpa.repository;

import codelook.jpa.model.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoRepo extends JpaRepository <OrderInfo, Long>{
}
