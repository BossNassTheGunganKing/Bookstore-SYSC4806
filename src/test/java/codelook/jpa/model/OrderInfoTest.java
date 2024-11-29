package codelook.jpa.model;

import codelook.jpa.StaticData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import codelook.jpa.order.model.*;

class OrderInfoTest {

    private OrderInfo orderInfo;


    @BeforeEach
    void setUp() {
        // Clone StaticData to prevent modifications to shared objects
        orderInfo = new OrderInfo(OrderStatus.IN_CART, new ArrayList<>(StaticData.orderInfo1.getItems()));
    }

    @Test
    void getAndSetOrderStatus() {
        assertEquals(OrderStatus.IN_CART, orderInfo.getOrderStatus());
        orderInfo.setOrderStatus(OrderStatus.PENDING);
        assertEquals(OrderStatus.PENDING, orderInfo.getOrderStatus());
    }

    @Test
    void getAndSetItems() {
        assertEquals(1, orderInfo.getItems().size());
        assertEquals(StaticData.orderItem1, orderInfo.getItems().get(0));
        orderInfo.setItems(new ArrayList<>());
        assertEquals(0, orderInfo.getItems().size());
    }

    @Test
    void addItem() {
        OrderItem newItem = new OrderItem(StaticData.listing3, 2);
        newItem.setPriceAtPurchase(StaticData.listing3.getDiscountedPrice());
        orderInfo.addItem(newItem);

        assertEquals(2, orderInfo.getItems().size());
        assertEquals(newItem, orderInfo.getItems().get(1));
    }

    @Test
    void removeItem() {
        OrderItem itemToRemove = orderInfo.getItems().get(0);
        orderInfo.removeItem(itemToRemove);

        assertEquals(0, orderInfo.getItems().size());
    }

    @Test
    void calculateTotalPrice() {
        OrderItem newItem = new OrderItem(StaticData.listing2, 2);
        newItem.setPriceAtPurchase(StaticData.listing2.getDiscountedPrice());
        orderInfo.addItem(newItem);

        BigDecimal expectedTotal = StaticData.orderItem1.getPriceAtPurchase()
                .multiply(new BigDecimal(StaticData.orderItem1.getQuantity()))
                .add(newItem.getPriceAtPurchase().multiply(new BigDecimal(newItem.getQuantity())));

        assertEquals(expectedTotal, orderInfo.getTotalPrice());
    }

    @Test
    void testOrderStatusTransitions() {
        assertEquals(OrderStatus.IN_CART, orderInfo.getOrderStatus());

        orderInfo.checkout();
        assertEquals(OrderStatus.PENDING, orderInfo.getOrderStatus());

        orderInfo.shipOrder();
        assertEquals(OrderStatus.SHIPPED, orderInfo.getOrderStatus());

        orderInfo.deliverOrder();
        assertEquals(OrderStatus.DELIVERED, orderInfo.getOrderStatus());
    }

    @Test
    void testImmutableStaticData() {
        OrderInfo clonedOrder = new OrderInfo(OrderStatus.IN_CART, new ArrayList<>(StaticData.orderInfo1.getItems()));
        clonedOrder.addItem(new OrderItem(StaticData.listing3, 1));

        assertEquals(1, StaticData.orderInfo1.getItems().size()); // Static data remains unaffected
        assertEquals(2, clonedOrder.getItems().size());
    }
}