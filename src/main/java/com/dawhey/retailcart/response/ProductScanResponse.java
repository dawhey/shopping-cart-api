package com.dawhey.retailcart.response;



public class ProductScanResponse extends StatusResponse {

    private Long purchaseActionId;

    public ProductScanResponse(String status, long purchaseActionId) {
        super(status);
        this.purchaseActionId = purchaseActionId;
    }

    public Long getPurchaseActionId() {
        return purchaseActionId;
    }

    public void setPurchaseActionId(Long purchaseActionId) {
        this.purchaseActionId = purchaseActionId;
    }
}
