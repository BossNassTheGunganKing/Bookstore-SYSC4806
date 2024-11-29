package codelook.jpa.order.repository;

import codelook.jpa.order.model.*;
import codelook.jpa.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderInfoRepo extends JpaRepository <OrderInfo, Long>{
    Optional<OrderInfo> findByOrderStatus(OrderStatus orderStatus);

    Optional<OrderInfo> findByUserAndOrderStatus(UserInfo currentUser, OrderStatus orderStatus);

    List<OrderInfo> findByUser(UserInfo currentUser);
}
