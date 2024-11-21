package codelook.jpa.controller;

import codelook.jpa.model.OrderInfo;
import codelook.jpa.repository.OrderInfoRepo;
import codelook.jpa.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    /**
     * Display the list of orders for the current user.
     */
    @GetMapping("/orders")
    public String viewOrders(Model model) {
        model.addAttribute("orders", shoppingCartService.getOrdersForCurrentUser());
        return "orders";
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        OrderInfo order = orderInfoRepo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        model.addAttribute("order", order);
        return "orderDetails";
    }
}
