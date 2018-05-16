package com.dawhey.retailcart.entities;

import javax.persistence.*;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private CartUser currentUser;

    @OneToOne(mappedBy = "cart")
    private PurchaseAction purchaseAction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CartUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(CartUser currentUser) {
        this.currentUser = currentUser;
    }

    public PurchaseAction getPurchaseAction() {
        return purchaseAction;
    }

    public void setPurchaseAction(PurchaseAction purchaseAction) {
        this.purchaseAction = purchaseAction;
    }
}
