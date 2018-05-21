package com.dawhey.retailcart.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private long id;

    private float price;

    private String name;

    private float weight;

    private float weightVariance;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="purchase_action_id")
    private PurchaseAction purchaseAction;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeightVariance() {
        return weightVariance;
    }

    public void setWeightVariance(float weightVariance) {
        this.weightVariance = weightVariance;
    }

    public PurchaseAction getPurchaseAction() {
        return purchaseAction;
    }

    public void setPurchaseAction(PurchaseAction purchaseAction) {
        this.purchaseAction = purchaseAction;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
