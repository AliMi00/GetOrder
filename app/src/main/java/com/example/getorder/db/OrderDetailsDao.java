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

    @Delete
    void delete(OrderDetails...orderDetails);

    @Update
    void update(OrderDetails orderDetails);

    @Query("DELETE FROM OrderDetails")
    void deleteAll();

    @Query("SELECT * FROM OrderDetails ")
    LiveData<List<Product>> getAllOrderDetails();

    @Query("SELECT * FROM OrderDetails WHERE OrderDetails.orderId = :oId ")
    LiveData<List<Product>> getOrderDetailsByOrderId(int oId);


}
