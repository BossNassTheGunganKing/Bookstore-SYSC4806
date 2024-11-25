package codelook.jpa.controller;

import codelook.jpa.commands.PlaceOrderCommand;
import codelook.jpa.commands.PlaceOrderCommandHandler;
import codelook.jpa.model.OrderInfo;
import codelook.jpa.model.OrderStatus;
import codelook.jpa.model.UserInfo;
import codelook.jpa.queries.GetOrdersQuery;
import codelook.jpa.queries.GetOrdersQueryHandler;
import codelook.jpa.repository.OrderInfoRepo;
import codelook.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    @Autowired
    private GetOrdersQueryHandler getOrdersHandler;

    @Autowired
    private PlaceOrderCommandHandler placeOrderHandler;


    public OrderController(UserService userService, PlaceOrderCommandHandler placeOrderHandler) {
        this.userService = userService;
        this.placeOrderHandler = placeOrderHandler;
    }

    /**
     * Display the list of orders for the current user.
     */
//    @GetMapping("/orders")
//    public String viewOrders(Model model) {
//        try {
//            UserInfo currentUser = userService.getCurrentUser(); // Get the logged-in user
//
//            // Filter orders to exclude those with status "IN_CART"
//            List<OrderInfo> orders = orderInfoRepo.findByUser(currentUser).stream()
//                    .filter(order -> order.getOrderStatus() != OrderStatus.IN_CART)
//                    .collect(Collectors.toList());
//
//            model.addAttribute("orders", orders);
//            return "orders";
//        } catch (Exception e) {
//            model.addAttribute("error", "Failed to load orders: " + e.getMessage());
//            return "account";
//        }
//    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        try {
            OrderInfo order = getOrdersHandler.handleOrderById(id); // Use a query handler for fetching order by ID
            model.addAttribute("order", order);

            return "orderDetails";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Order not found: " + e.getMessage());
            return "orders"; // Redirect back to orders page on error
        }
    }

    @GetMapping("/orders")
    public String viewOrders(Model model) {
        try {
            UserInfo currentUser = userService.getCurrentUser(); // Retrieve the logged-in user
            GetOrdersQuery query = new GetOrdersQuery(currentUser);
            List<OrderInfo> orders = getOrdersHandler.handle(query);
            model.addAttribute("orders", orders);
            return "orders";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load orders: " + e.getMessage());
            return "account";
        }
    }
}
