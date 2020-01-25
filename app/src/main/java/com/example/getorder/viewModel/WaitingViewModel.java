package com.example.getorder.viewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.getorder.model.Order;
import com.example.getorder.model.OrderStatus;
import com.example.getorder.model.Product;
import com.example.getorder.services.OrdersServices;
import com.example.getorder.services.ProductService;

import java.util.List;

public class WaitingViewModel extends AndroidViewModel {
    private OrdersServices ordersServices;
    private LiveData<List<Order>> allOpenOrders;

    public WaitingViewModel(@NonNull Application application) {
        super(application);
        ordersServices = new OrdersServices(application);
        allOpenOrders = ordersServices.getOpenOrders();
    }

    //get all Open Orders
    public LiveData<List<Order>> getAllOpenOrders() {
        return allOpenOrders;
    }

    //to editing the existing open order
    public void editOrder(int orderId) {
        //todo go to another activity for editing the order
    }
    //set order status
    public void setOrderStatus(int id , OrderStatus status){
        ordersServices.setStatusOrder(id,status);
    }
    //delete the order completely
    public void deleteOrder(int id){
        ordersServices.deleteOrder(id);
    }
}