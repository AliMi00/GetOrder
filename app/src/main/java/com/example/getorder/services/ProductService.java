package com.example.getorder.services;

import android.app.Application;
import android.os.AsyncTask;
import android.text.style.UpdateAppearance;

import androidx.lifecycle.LiveData;

import com.example.getorder.db.ProductDao;
import com.example.getorder.db.ProductDb;
import com.example.getorder.model.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    public ProductService(Application application) {
        ProductDb db = ProductDb.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProduct();
    }
    //insert product to local db
    public void insert(Product product){
        new InsertProductAsync(productDao).execute(product);
    }
    public void delete(Product product){
        new DeleteProductAsync(productDao).execute(product);
    }
    public void update(Product product){
        new UpdateProductAsync( productDao).execute(product);
    }
    //get all product on live data from local db
    public LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }

    //async task for insert product to local db
    private static class InsertProductAsync extends AsyncTask<Product ,Void ,Void>{

        private ProductDao productDao;
        private InsertProductAsync(ProductDao productDao){
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }
    //async task for delete product from local db
    private static class DeleteProductAsync extends AsyncTask<Product ,Void ,Void>{

        private ProductDao productDao;
        private DeleteProductAsync(ProductDao productDao){
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.delete(products);
            return null;

        }
    }
    //async task for update product from local db
    private static class UpdateProductAsync extends AsyncTask<Product ,Void ,Void>{

        private ProductDao productDao;
        private UpdateProductAsync(ProductDao productDao){
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.update(products[0]);
            return null;

        }
    }
}
