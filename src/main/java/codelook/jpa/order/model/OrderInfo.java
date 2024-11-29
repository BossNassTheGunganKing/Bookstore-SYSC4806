package codelook.jpa.order.model;


import codelook.jpa.model.OrderItem;
import codelook.jpa.model.OrderStatus;
import codelook.jpa.model.UserInfo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    public OrderStatus orderStatus;

    private LocalDateTime createdDate;
    private BigDecimal totalPrice;

    @ManyToOne
    private UserInfo user; // Link the order to a specific user

    public OrderInfo() {
        this.orderStatus = OrderStatus.IN_CART; // Default status
        this.totalPrice = BigDecimal.ZERO;
        this.createdDate = LocalDateTime.now();
    }

    public OrderInfo(OrderStatus orderStatus, List<OrderItem> items) {
        this.orderStatus = orderStatus;
        this.items = new ArrayList<>(items); // Ensure mutable copy
        calculateTotalPrice();
    }

    // Add an item to the order/cart
    public void addItem(OrderItem item) {
        if (item.getPriceAtPurchase() == null) {
            // Set priceAtPurchase based on the ListingInfo
            item.setPriceAtPurchase(item.getListing().getDiscountedPrice() != null
                    ? item.getListing().getDiscountedPrice()
                    : item.getListing().getOriginalPrice());
        }
        items.add(item);
        calculateTotalPrice();
    }

    // Remove an item from the order/cart
    public void removeItem(OrderItem item) {
        items.remove(item);
        calculateTotalPrice();
    }

    // Calculate total price
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(item -> item.getPriceAtPurchase().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Transition order status
    public void checkout() {
        if (this.orderStatus != OrderStatus.IN_CART) {
            throw new IllegalStateException("Only orders in cart can be checked out.");
        }
        this.orderStatus = OrderStatus.PENDING;
    }

    public void shipOrder() {
        if (this.orderStatus != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be shipped.");
        }
        this.orderStatus = OrderStatus.SHIPPED;
    }

    public void deliverOrder() {
        if (this.orderStatus != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Only shipped orders can be delivered.");
        }
        this.orderStatus = OrderStatus.DELIVERED;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = new ArrayList<>(items); // Ensure mutable list
        calculateTotalPrice();
    }

    public BigDecimal getTotalPrice() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(item -> item.getPriceAtPurchase().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
