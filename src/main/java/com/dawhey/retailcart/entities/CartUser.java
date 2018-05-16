package com.dawhey.retailcart.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class CartUser {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    private String userName;

    @NotNull
    private String password;

    private String token;

    @OneToOne(mappedBy = "currentUser")
    private ShoppingCart cart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}
