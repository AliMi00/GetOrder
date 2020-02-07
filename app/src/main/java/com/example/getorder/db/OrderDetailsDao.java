package com.example.getorder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.Product;

import java.util.List;

@Dao
public interface OrderDetailsDao {

    @Insert
    void insert(OrderDetails orderDetails);
    @Insert
    void insert(OrderDetails...orderDetails);

    @Delete
    void delete(OrderDetails...orderDetails);

    @Update
    void update(OrderDetails orderDetails);

    @Query("DELETE FROM order_details")
    void deleteAll();

    @Query("SELECT * FROM order_details ")
    LiveData<List<OrderDetails>> getAllOrderDetails();

    @Query("SELECT * FROM order_details WHERE order_details.orderId = :oId ")
    LiveData<List<OrderDetails>> getOrderDetailsByOrderId(int oId);

    @Query("DELETE FROM order_details WHERE orderId = :oId")
    void delete(int oId);

}
