package com.example.getorder.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "orders" )
public class Order {
    @Ignore
    public Order() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int tableNum;
    private int amountSell;

    public void setTableNum(int tableNum) {
        this.tableNum = tableNum;
    }

    public void setAmountSell(int amountSell) {
        this.amountSell = amountSell;
    }

    public void setAmountBuy(int amountBuy) {
        this.amountBuy = amountBuy;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCreateDate(int createDate) {
        this.createDate = createDate;
    }

    private int amountBuy;
    private int profit;
    private int status;
    private int createDate;


    public int getCreateDate() {
        return createDate;
    }

    public Order(int tableNum, int amountSell, int amountBuy, int profit, int status, int createDate) {
        this.tableNum = tableNum;
        this.amountSell = amountSell;
        this.amountBuy = amountBuy;
        this.profit = profit;
        this.status = status;
        this.createDate = createDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getTableNum() {
        return tableNum;
    }

    public int getAmountSell() {
        return amountSell;
    }

    public int getAmountBuy() {
        return amountBuy;
    }

    public int getProfit() {
        return profit;
    }

    public int getStatus() {
        return status;
    }

}
