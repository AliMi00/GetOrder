package com.example.getorder.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.getorder.model.Order;
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.OrderReport;
import com.example.getorder.model.Product;
import com.example.getorder.services.OrdersServices;
import com.example.getorder.services.ProductService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDailyReportViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private OrdersServices ordersServices;
    private ProductService productService;
    private LiveData<List<OrderReport>> reports;
    private LiveData<List<Product>> allProducts;
    private LiveData<List<Order>> orders;

    private int date1;

    public int getDate1() {
        return date1;
    }

    public void setDate1(int date1) {
        this.date1 = date1;
    }

    public int getDate2() {
        return date2;
    }

    public void setDate2(int date2) {
        this.date2 = date2;
    }

    private int date2;

    public LiveData<List<Order>> getOrders() {
        return orders;
    }


    public OrderDailyReportViewModel(@NonNull Application application) {
        super(application);
        ordersServices = new OrdersServices(application);
        productService = new ProductService(application);
        setDate1(currentDate());
        setDate2(currentDate());
        reports = ordersServices.getReportDaily(date1,date2);
        allProducts = productService.getAllProducts();
        orders = ordersServices.getAllOrders();

    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }

    public LiveData<List<OrderReport>> getDailyReport(){
        return reports;
    }

    public int currentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        //You can change "yyyyMMdd as per your requirement
        int currentDateAndTime = Integer.valueOf(sdf.format(new Date()));
        return currentDateAndTime;
    }

    public void updateReportList(){
        reports = null;
        reports = ordersServices.getReportDaily(date1,date2);
    }
}
