package codelook.jpa.commands;

import codelook.jpa.model.OrderInfo;
import codelook.jpa.model.OrderStatus;
import codelook.jpa.model.UserInfo;
import codelook.jpa.repository.OrderInfoRepo;
import codelook.jpa.repository.UserInfoRepo;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PlaceOrderCommandHandler {

    private final OrderInfoRepo orderInfoRepo;
    private final UserInfoRepo userInfoRepo;

    public PlaceOrderCommandHandler(OrderInfoRepo orderInfoRepo, UserInfoRepo userInfoRepo) {
        this.orderInfoRepo = orderInfoRepo;
        this.userInfoRepo = userInfoRepo;
    }

    public void handle(PlaceOrderCommand command) {
        // Retrieve the user based on userId
        UserInfo user = userInfoRepo.findById(command.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Retrieve the user's cart
        OrderInfo cart = orderInfoRepo.findByUserAndOrderStatus(user, OrderStatus.IN_CART)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Update cart to reflect order placement
        cart.checkout();
        cart.setCreatedDate(LocalDateTime.now());
        orderInfoRepo.save(cart);

        // (Optional) Add logic to validate payment details using command.getCardNumber(), etc.
    }
}