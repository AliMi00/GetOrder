package com.example.getorder.model;

public class OrderReport {

    private String productName;
    private int productQuantity;
    private int productSum;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getProductSum() {
        return productSum;
    }

    public void setProductSum(int productSum) {
        this.productSum = productSum;
    }
}
