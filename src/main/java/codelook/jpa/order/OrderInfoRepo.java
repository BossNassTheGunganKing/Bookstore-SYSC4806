package codelook.jpa.order;


import codelook.jpa.user.*;
import codelook.jpa.order.*;
import codelook.jpa.book.*;
import codelook.jpa.image.*;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderInfoRepo extends JpaRepository <OrderInfo, Long>{
    Optional<OrderInfo> findByOrderStatus(OrderStatus orderStatus);

    Optional<OrderInfo> findByUserAndOrderStatus(UserInfo currentUser, OrderStatus orderStatus);

    List<OrderInfo> findByUser(UserInfo currentUser);
}
