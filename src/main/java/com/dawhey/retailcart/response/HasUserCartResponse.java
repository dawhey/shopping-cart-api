package com.dawhey.retailcart.response;

public class HasUserCartResponse extends StatusResponse {

    private boolean hasCart;

    private long cartId;

    public HasUserCartResponse(String status, boolean hasCart, long cartId) {
        super(status);
        this.hasCart = hasCart;
        this.cartId = cartId;
    }

    public boolean isHasCart() {
        return hasCart;
    }

    public void setHasCart(boolean hasCart) {
        this.hasCart = hasCart;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }
}
