package com.dawhey.retailcart.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchase_action")
public class PurchaseAction {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    private boolean isCompleted;

    @OneToMany(mappedBy = "purchaseAction", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
