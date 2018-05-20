package com.dawhey.retailcart.response;

import com.dawhey.retailcart.entities.Product;

import java.util.Set;

public class GetProductsResponse extends StatusResponse{

    private Set<Product> products;

    public GetProductsResponse(String status, Set<Product> products) {
        super(status);
        this.products = products;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
