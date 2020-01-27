package com.example.getorder.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity( tableName = "order_details",
        foreignKeys ={
                    @ForeignKey(entity = Product.class,
                        parentColumns = "id",
                        childColumns = "productId",
                        onDelete = CASCADE)
                    ,@ForeignKey(entity = Order.class,
                        parentColumns = "id",
                        childColumns = "orderId",
                        onDelete = CASCADE)},
        indices = {@Index(value = {"productId"}),@Index(value = "orderId")}
        )

public class OrderDetails {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderId;
    private int productId;
    private String description;
    private int sellPrice;
    private int buyPrice;
    private int quantity;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private double discount;

    public void setId(int id) {
        this.id = id;
    }

    public OrderDetails(int orderId, int productId, String description, int sellPrice, int buyPrice,int quantity, double discount) {
        this.orderId = orderId;
        this.productId = productId;
        this.description = description;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.discount = discount;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
