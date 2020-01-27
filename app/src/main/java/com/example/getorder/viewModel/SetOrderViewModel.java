package com.example.getorder.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.getorder.model.Order;
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.Product;
import com.example.getorder.services.OrdersServices;
import com.example.getorder.services.ProductService;

import java.util.ArrayList;
import java.util.List;


public class SetOrderViewModel extends AndroidViewModel {

    private OrdersServices ordersServices;
    private LiveData<List<Product>> allProducts;
    private ProductService productService;
    private List<OrderDetails> tempOrderDetailsList = new ArrayList<>() ;
    private Order TempOrder;
    private OrderDetails orderDetails;

    public SetOrderViewModel(@NonNull Application application) {
        super(application);
        ordersServices = new OrdersServices(application);
        productService = new ProductService(application);
        allProducts =  productService.getAllProducts();



    }

    public LiveData<List<Product>> getAllProduct(){
        return allProducts;
    }

    //return orderDetail

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Order getTempOrder() {
        return TempOrder;
    }

    public void setTempOrder(Order tempOrder) {
        this.TempOrder = tempOrder;
    }

    public List<OrderDetails> getTempOrderDetailsList() {
        if(tempOrderDetailsList == null){
            return tempOrderDetailsList = new ArrayList<>();
        }
        else{
            return tempOrderDetailsList;
        }
    }

    public void setTempOrderDetailsList(List<OrderDetails> tempOrderDetailsList) {
        this.tempOrderDetailsList = tempOrderDetailsList;

    }

    public void setSingleTempOrderDetailsList(OrderDetails orderDetails) {
        tempOrderDetailsList.add(orderDetails);

    }

    //add data to db

    //add TempOrder to local db with TempOrder and list of orderDetails
    public int addOrder(Order order, List<OrderDetails> detailsList){

        int id = ordersServices.addOrder(order,detailsList);
        tempOrderDetailsList = new ArrayList<>();
        setTempOrder(null);
        return id;
    }
    //add TempOrder with existing list and TempOrder in this modelView
    public int addOrder(){
        int id = ordersServices.addOrder(getTempOrder(), getTempOrderDetailsList());
        tempOrderDetailsList = new ArrayList<>();
        setTempOrder(null);
        return id;
    }
    //add single orderDetails to this list
    public void setSingleOrderDetailsToList(OrderDetails orderDetails){
        if(tempOrderDetailsList ==null){
            tempOrderDetailsList = new ArrayList<>();
        }
        for(OrderDetails od : tempOrderDetailsList){
            if(od.getProductId() == orderDetails.getProductId()){
                tempOrderDetailsList.remove(od);
            }
        }
        tempOrderDetailsList.add(orderDetails);
    }


}
