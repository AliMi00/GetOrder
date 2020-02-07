package com.example.getorder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.getorder.model.Order;
import com.example.getorder.model.Product;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    long insert(Order order);
    @Delete
    void delete(Order... order);
    @Update
    void update(Order order);

    @Query("SELECT * FROM orders")
    LiveData<List<Order>> getOrders();

    @Query("select * from  orders where orders.id = :orderId")
    Order getOrder(int orderId);

    @Query("delete from orders where id = :orderId")
    void delete(int orderId);

    @Query("SELECT * FROM orders where status = :orderStatus")
    LiveData<List<Order>> getOpenOrders(int orderStatus);

    @Query("select * from  orders where status = :orderStatus order by id desc LIMIT 1")
    LiveData<List<Order>> getLastOrder(int orderStatus);

    @Query("delete from orders where status = :orderStatus")
    void deleteOldNewOrders(int orderStatus);

}
