package codelook.jpa.order;

import codelook.jpa.user.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    /**
     * Display the list of orders for the current user.
     */
    @GetMapping("/orders")
    public String viewOrders(Model model) {
        try {
            UserInfo currentUser = userService.getCurrentUser(); // Get the logged-in user

            // Filter orders to exclude those with status "IN_CART"
            List<OrderInfo> orders = orderInfoRepo.findByUser(currentUser).stream()
                    .filter(order -> order.getOrderStatus() != OrderStatus.IN_CART)
                    .collect(Collectors.toList());

            model.addAttribute("orders", orders);
            return "orders";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load orders: " + e.getMessage());
            return "account";
        }
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        OrderInfo order = orderInfoRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "orderDetails";
    }
}
