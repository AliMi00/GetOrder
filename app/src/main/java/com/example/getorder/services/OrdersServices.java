package com.example.getorder.services;

import android.app.Application;
import android.renderscript.Sampler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.getorder.db.OrderDao;
import com.example.getorder.db.OrderDb;
import com.example.getorder.db.OrderDetailsDao;
import com.example.getorder.db.ProductDao;
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.OrderStatus;
import com.example.getorder.model.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersServices {
    private OrderDao orderDao;
    private ProductDao productDao;
    private LiveData<List<Order>> getAllOrders;
    private List<Order> ordersList;
    private LiveData<List<Product>> productLive;
    private Application application;
    private OrderDetailsDao orderDetailsDao;


    public OrdersServices(Application application) {
        OrderDb db = OrderDb.getInstance(application);
        productDao = db.productDao();
        orderDao = db.orderDao();
        getAllOrders = orderDao.getOrders();
        productLive = productDao.getAllProduct();
        orderDetailsDao = db.orderDetailsDao();
        this.application = application;
    }
    //add order to local db
    public int addOrder(Order order,List<OrderDetails> orderDetails){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        //You can change "yyyyMMdd as per your requirement

        int currentDateAndTime = Integer.valueOf(sdf.format(new Date()));
        int id;
        int sumBuy = 0;
        int sumSell = 0;
        //calculating amount buy and sell
        for(OrderDetails od : orderDetails){
            sumBuy += od.getBuyPrice();
            sumSell += (od.getSellPrice()*od.getDiscount());
        }
        order.setAmountBuy(sumBuy);
        order.setAmountSell(sumSell);
        order.setProfit(sumSell-sumBuy);
        order.setCreateDate(currentDateAndTime);
        order.setStatus(0);
        //insert order to local db
        id =(int) orderDao.insert(order);
        //add order id to orderDetails and add to local db
        for(OrderDetails od : orderDetails){
            od.setOrderId(id);
            orderDetailsDao.insert(od);
        }
        return id;
    }
    public void setStatusOrder(int orderId, OrderStatus orderStatus){
        Order order = orderDao.getOrder(orderId);
        // todo add status to order
        order.setStatus(orderStatus.ordinal());
        orderDao.update(order);

    }
}
