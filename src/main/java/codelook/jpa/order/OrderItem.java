package codelook.jpa.order;

import codelook.jpa.book.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    private int quantity;

    @ManyToOne
    private ListingInfo listing;

    private BigDecimal priceAtPurchase;

    public OrderItem() {}

    public OrderItem(ListingInfo listing, int quantity) {
        this.quantity = quantity;
        this.listing = listing;
        // Use discountedPrice if available, otherwise originalPrice
        this.priceAtPurchase = listing.getDiscountedPrice() != null
                ? listing.getDiscountedPrice()
                : listing.getOriginalPrice();
    }

    public BigDecimal getTotalPrice() {
        return priceAtPurchase.multiply(new BigDecimal(quantity));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public ListingInfo getListing() {
        return listing;
    }

    public void setListing(ListingInfo listing) {
        this.listing = listing;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
