package codelook.jpa.order;

import codelook.jpa.user.*;
import codelook.jpa.order.*;
import codelook.jpa.book.*;
import codelook.jpa.image.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * View the user's shopping cart.
     */
    @GetMapping("/cart")
    public String viewCart(Model model) {
        OrderInfo cart = shoppingCartService.getCart();
        if (cart == null) {
            cart = new OrderInfo(); // Initialize a new empty cart
            cart.setOrderStatus(OrderStatus.IN_CART);
            cart.setItems(new ArrayList<>()); // Ensure items list is not null
        }
        model.addAttribute("cart", cart);
        return "cart";
    }

    /**
     * Display the form to add a new item to the cart.
     */
    @GetMapping("/cart/add")
    public String addItemForm(Model model) {
        model.addAttribute("listings", shoppingCartService.getAvailableListings());
        return "addItem";
    }

    /**
     * Add an item to the shopping cart.
     */
    @PostMapping("/cart/add")
    public String addItem(@RequestParam Long listingId, @RequestParam int quantity, Model model) {
        try {
            shoppingCartService.addItemToCart(listingId, quantity);
            return "redirect:/cart";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("listings", shoppingCartService.getAvailableListings());
            return "allListings"; // Update to return appropriate page
        }
    }

    /**
     * Remove an item from the shopping cart.
     */
    @PostMapping("/cart/remove")
    public String removeItem(@RequestParam Long itemId, Model model) {
        try {
            shoppingCartService.removeItemFromCart(itemId);
            return "redirect:/cart";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "cart"; // Reload the cart page with error
        }
    }

    /**
     * Update the quantity of an item in the shopping cart.
     */
    @PostMapping("/cart/update")
    public String updateItem(@RequestParam Long itemId, @RequestParam int quantity, Model model) {
        try {
            shoppingCartService.updateCartItemQuantity(itemId, quantity);
            return "redirect:/cart"; // Redirect on success
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cart", shoppingCartService.getCart());
            return "cart"; // Show the cart page with the error message
        }
    }

    /**
     * Proceed to checkout.
     */
    @GetMapping("/checkout")
    public String checkout(Model model) {
        try {
            OrderInfo order = shoppingCartService.checkout();
            model.addAttribute("order", order);
            return "checkout";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("cart", shoppingCartService.getCart());
            return "cart"; // Reload the cart page with error
        }
    }

    /**
     * Show the checkout confirmation page.
     */
    @GetMapping("/checkout/confirm")
    public String showCheckoutForm(Model model) {
        OrderInfo cart = shoppingCartService.getCart();
        if (cart.getItems().isEmpty()) {
            model.addAttribute("error", "Your cart is empty. Add items before proceeding to checkout.");
            return "cart"; // Redirect to the cart page if empty
        }
        model.addAttribute("order", cart);
        return "checkoutConfirm";
    }

    /**
     * Process the checkout and place the order.
     */
    @PostMapping("/checkout/placeOrder")
    public String placeOrder(@RequestParam String cardNumber,
                             @RequestParam String cardExpiry,
                             @RequestParam String cardCVC,
                             Model model) {
        try {
            OrderInfo order = shoppingCartService.placeOrder(cardNumber, cardExpiry, cardCVC);
            model.addAttribute("order", order); // Add the order object to the model
            return "orderConfirmation";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("order", shoppingCartService.getCart());
            return "checkoutConfirm";
        }
    }
}
