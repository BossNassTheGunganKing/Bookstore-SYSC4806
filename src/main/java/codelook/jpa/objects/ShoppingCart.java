package codelook.jpa.objects;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<Objects> cartContents;

    public ShoppingCart() {
        this.cartContents = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Objects> getCartContents() {
        return cartContents;
    }

    public void setCartContents(List<Objects> cartContents) {
        this.cartContents = cartContents;
    }

}
