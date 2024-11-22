package codelook.jpa.service;

import codelook.jpa.model.UserInfo;
import codelook.jpa.repository.OrderInfoRepo;
import codelook.jpa.repository.ListingInfoRepo;
import codelook.jpa.repository.UserInfoRepo;
import codelook.jpa.model.OrderInfo;
import codelook.jpa.model.ListingInfo;
import codelook.jpa.model.OrderItem;
import codelook.jpa.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private OrderInfoRepo orderInfoRepo;

    @Autowired
    private ListingInfoRepo listingInfoRepo;

    @Autowired
    private UserInfoRepo userInfoRepo;

    private static final String CART_STATUS = "IN_CART";

    /**
     * Get the current user's cart. Create a new one if it doesn't exist.
     */
    public OrderInfo getCart() {
        UserInfo currentUser = getCurrentUser();
        return orderInfoRepo.findByUserAndOrderStatus(currentUser, OrderStatus.IN_CART)
                .orElseGet(() -> createNewCart(currentUser));
    }

    /**
     * Add an item to the cart.
     */
    public void addItemToCart(Long listingId, int quantity) {
        ListingInfo listing = listingInfoRepo.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        if (listing.getRemainingCopies() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        OrderInfo cart = getCart();

        // Check if the item is already in the cart
        OrderItem existingItem = cart.getItems().stream()
                .filter(item -> item.getListing().getId().equals(listingId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            OrderItem newItem = new OrderItem(listing, quantity);
            cart.addItem(newItem);
        }

        // Update inventory
        listing.removeRemainingCopies(quantity);
        listingInfoRepo.save(listing);

        // Save cart
        orderInfoRepo.save(cart);
    }

    /**
     * Remove an item from the cart.
     */
    public void removeItemFromCart(Long itemId) {
        OrderInfo cart = getCart();

        OrderItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        // Restore stock
        ListingInfo listing = itemToRemove.getListing();
        listing.addRemainingCopies(itemToRemove.getQuantity());
        listingInfoRepo.save(listing);

        // Remove item and save cart
        cart.removeItem(itemToRemove);
        orderInfoRepo.save(cart);
    }

    /**
     * Update the quantity of an item in the cart.
     */
    public void updateCartItemQuantity(Long itemId, int newQuantity) {
        OrderInfo cart = getCart();

        OrderItem itemToUpdate = cart.getItems().stream()
                .filter(item -> item.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));

        int quantityDifference = newQuantity - itemToUpdate.getQuantity();

        if (quantityDifference > 0) { // Adding more items
            if (itemToUpdate.getListing().getRemainingCopies() < quantityDifference) {
                throw new RuntimeException("Not enough stock available");
            }
            itemToUpdate.getListing().removeRemainingCopies(quantityDifference);
        } else if (quantityDifference < 0) { // Reducing items
            itemToUpdate.getListing().addRemainingCopies(-quantityDifference);
        }

        itemToUpdate.setQuantity(newQuantity);
        listingInfoRepo.save(itemToUpdate.getListing());
        orderInfoRepo.save(cart);
    }

    /**
     * Checkout the cart, transitioning the order status to PENDING.
     */
    public OrderInfo checkout() {
        OrderInfo cart = getCart();

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        cart.checkout();
        return orderInfoRepo.save(cart);
    }

    /**
     * Create a new cart for the user.
     */
    private OrderInfo createNewCart(UserInfo user) {
        OrderInfo newCart = new OrderInfo();
        newCart.setUser(user);
        newCart.setOrderStatus(OrderStatus.IN_CART);
        return orderInfoRepo.save(newCart);
    }

    /**
     * Placeholder for fetching the currently logged-in user.
     */
    private UserInfo getCurrentUser() {
        return userInfoRepo.findById(1L).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Fetch all available listings for display on the "add item" page.
     */
    public List<ListingInfo> getAvailableListings() {
        return listingInfoRepo.findAll();
    }

    /**
     * Fetch all orders for the currently logged-in user.
     */
    public List<OrderInfo> getOrdersForCurrentUser() {
        UserInfo currentUser = getCurrentUser();
        return orderInfoRepo.findByUser(currentUser);
    }

    /**
     * Validate payment details and place the order.
     */
    public OrderInfo placeOrder(String cardNumber, String cardExpiry, String cardCVC) {
        OrderInfo cart = getCart();

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Add items before placing the order.");
        }

        // fake validation for payment details
        if (cardNumber == null || cardNumber.isEmpty() || cardExpiry == null || cardCVC == null) {
            throw new RuntimeException("Invalid payment details. Please fill in all fields.");
        }

        cart.setCreatedDate(LocalDateTime.now());

        // Transition the cart to a pending order
        cart.checkout();
        return orderInfoRepo.save(cart);
    }
}