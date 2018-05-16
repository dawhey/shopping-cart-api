package com.dawhey.retailcart.request;

public class UserCartBindingRequest {

    private String token;

    private String cartId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }
}
