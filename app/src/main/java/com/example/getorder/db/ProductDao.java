package com.example.getorder.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.getorder.model.Product;
import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void insert(Product product);

    @Delete
    void delete(Product...products);

    @Update
    void update(Product product);

    @Query("DELETE FROM Product")
    void deleteAll();

    @Query("SELECT * FROM Product ")
    LiveData<List<Product>> getAllProduct();

}
