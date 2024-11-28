package codelook.jpa.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class OrderEvent {
    @Id
    @GeneratedValue
    private Integer id;
    private String accountEmail;
    @OneToOne
    private OrderInfo orderInfo;
    private Long timestamp;


    public OrderEvent() {
        timestamp = new Date().getTime();
    }

    public OrderEvent(String accountEmail, OrderInfo orderInfo) {
        this.accountEmail = accountEmail;
        this.orderInfo = orderInfo;
        timestamp = new Date().getTime();
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getAccountEmail() {
        return accountEmail;
    }
    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
