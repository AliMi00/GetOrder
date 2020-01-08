package com.example.getorder.viewModel;

import android.app.Application;
import android.app.ListActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.getorder.model.Product;
import com.example.getorder.services.ProductService;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductService productService;
    private LiveData<List<Product>> allProduct;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        productService = new ProductService(application);
        allProduct = productService.getAllProducts();
    }
    public void insert(Product product){
        productService.insert(product);
    }
    public void update(Product product){
        productService.update(product);
    }
    public void delete(Product product){
        productService.delete(product);
    }
    public LiveData<List<Product>> getAllProduct(){
        return allProduct;
    }

}
