package codelook.jpa.cart.events;

public class CartCheckedOutEvent {
    private final Long cartId;

    public CartCheckedOutEvent(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCartId() {
        return cartId;
    }
}
