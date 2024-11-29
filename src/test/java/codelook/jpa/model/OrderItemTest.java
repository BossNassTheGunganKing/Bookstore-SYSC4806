package codelook.jpa.model;

import codelook.jpa.StaticData;
import codelook.jpa.order.model.OrderItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    private OrderItem orderItem;
    @BeforeEach
    void setUp() {
        orderItem = StaticData.orderItem1;
    }

    @Test
    void getQuantity() {
        assertEquals(1, orderItem.getQuantity());
    }

    @Test
    void getAndSetPurchasePrice(){
        assertEquals(BigDecimal.valueOf(19.99), orderItem.getPriceAtPurchase());
        orderItem.setPriceAtPurchase(BigDecimal.valueOf(1.0));
        assertEquals(BigDecimal.valueOf(1.0), orderItem.getPriceAtPurchase());
    }
}