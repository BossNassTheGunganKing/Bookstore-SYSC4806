package codelook.jpa.queries;

import codelook.jpa.model.OrderInfo;
import codelook.jpa.model.OrderStatus;
import codelook.jpa.model.UserInfo;
import codelook.jpa.repository.OrderInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetOrdersQueryHandler {

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    public GetOrdersQueryHandler(OrderInfoRepo orderInfoRepo) {
        this.orderInfoRepo = orderInfoRepo;
    }

    public List<OrderInfo> handle(GetOrdersQuery query) {
        UserInfo user = query.getUser();
        // Fetch orders for the user and exclude IN_CART orders
        return orderInfoRepo.findByUserAndOrderStatusNot(user, OrderStatus.IN_CART);
    }

    public OrderInfo handleOrderById(Long id) {
        return orderInfoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order with ID " + id + " not found"));
    }
}
