package codelook.jpa.model;

import codelook.jpa.StaticData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderInfoTest {

    private OrderInfo orderInfo;
    @BeforeEach
    void setUp() {
        orderInfo = StaticData.orderInfo1;
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
}