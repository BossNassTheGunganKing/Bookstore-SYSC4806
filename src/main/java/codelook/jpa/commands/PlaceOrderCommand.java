package codelook.jpa.commands;

import java.util.Map;

public class PlaceOrderCommand {
    private Long userId;
    private String cardNumber;
    private String cardExpiry;
    private String cardCVC;

    public PlaceOrderCommand(Long userId, String cardNumber, String cardExpiry, String cardCVC) {
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.cardExpiry = cardExpiry;
        this.cardCVC = cardCVC;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardExpiry() {
        return cardExpiry;
    }

    public String getCardCVC() {
        return cardCVC;
    }
}