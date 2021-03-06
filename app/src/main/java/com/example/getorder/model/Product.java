package com.example.getorder.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName ="Product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int sellPrice;
    private int buyPrice;
    private double discount;

    public void setId(int id) {
        this.id = id;
    }

    public Product(String title, String description, int sellPrice, int buyPrice, double discount) {
        this.title = title;
        this.description = description;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public void setTitle(String title) {
        this.title = title;
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
