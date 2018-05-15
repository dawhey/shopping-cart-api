package com.dawhey.retailcart.entities;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private long id;


    private String name;

    private float weight;

    private float weightVariance;

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
}
