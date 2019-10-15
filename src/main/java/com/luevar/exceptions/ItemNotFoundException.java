package com.luevar.exceptions;

public class ItemNotFoundException extends Exception {
    private String productName;

    public String getProductName() {
        return productName;
    }

    public ItemNotFoundException(String productName) {
        super();
        this.productName = productName;
    }
}
