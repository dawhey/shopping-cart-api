package com.dawhey.retailcart.response;



public class ProductScanResponse extends StatusResponse {


    private String productAction;

    public ProductScanResponse(String status, String productAction) {
        super(status);
        this.productAction = productAction;
    }

    public String getProductAction() {
        return productAction;
    }

    public void setProductAction(String productAction) {
        this.productAction = productAction;
    }
}
