package com.example.getorder.services;

import android.app.Application;
import android.os.AsyncTask;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrdersServices {
    private OrderDao orderDao;
    private ProductDao productDao;
    private List<Order> getAllOrders;
    private List<Order> ordersList;
    private LiveData<List<Product>> productLive;
    private Application application;
    private OrderDetailsDao orderDetailsDao;


    public OrdersServices(Application application) {
        OrderDb db = OrderDb.getInstance(application);
        productDao = db.productDao();
        orderDao = db.orderDao();
        getAllOrders = orderDao.getOrders().getValue();
        productLive = productDao.getAllProduct();
        orderDetailsDao = db.orderDetailsDao();
        this.application = application;
    }
    //add order to local db async
    public int addOrder(Order order, final List<OrderDetails> orderDetails){

        AsyncResponInt asyncResponInt = new AsyncResponInt() {
            @Override
            public void asyncRes(String string) {
                OrderDetails[] od = orderDetails.toArray(new OrderDetails[0]);
                for (int i = 0 ;i < od.length;i++ ) {
                    od[i].setOrderId(Integer.valueOf(string));
                    orderDetails.get(i).setOrderId(Integer.valueOf(string));
                }
                new AddOrderDetailToOrderAsync(orderDao,orderDetailsDao).execute(od);

            }
        };
        new InsertOrderAsync(orderDao,asyncResponInt).execute(order);
        return orderDetails.get(0).getOrderId();
    }
    //set status to order
    public void setStatusOrder(int orderId, OrderStatus orderStatus){
        Order order = orderDao.getOrder(orderId);
        order.setStatus(orderStatus.ordinal());
        orderDao.update(order);
    }
    //this method update the order buy checking the orderDetails of this order and set amounts and profit async
    public void updateOrder(int orderId){
        new UpdateOrderAsync(orderDao,orderDetailsDao).execute(orderId);
    }
    //delete order and orderDetails sync
    public void deleteOrder(int orderId){
        new DeleteOrderAsync(orderDao,orderDetailsDao).execute(orderId);
    }
    //update whole orderDetails with new List and update  order only open orders async
    public void updateOrderAndOrderDetailsWithList(int orderId,List<OrderDetails> orderDetails){

        if(orderDao.getOrder(orderId).getStatus() == OrderStatus.OPEN.ordinal()){
            orderDetailsDao.delete(orderId);
            for(OrderDetails od:orderDetails){
                if(od.getOrderId() != orderId){
                    orderDetails.remove(od);
                }
            }
            OrderDetails[] od = orderDetails.toArray(new OrderDetails[0]);
            new AddOrderDetailToOrderAsync(orderDao,orderDetailsDao).execute(od);
        }
    }
    //add single orderDetail and update order again async
    public void addOrderDetailToOrder(OrderDetails orderDetails){

        new AddOrderDetailToOrderAsync(orderDao,orderDetailsDao).execute(orderDetails);
    }
    //delete orderdetails and update order async
    public void deleteOrderDetail(OrderDetails orderDetails){
        new DeleteOrderDetailAsync(orderDao,orderDetailsDao).execute(orderDetails);
    }
    //check all orderDetails list belong to  same order
    public List<OrderDetails> validateOrderDetails(int orderId ,List<OrderDetails> orderDetails){
        List<OrderDetails> finalOd = new ArrayList<OrderDetails>();
        for(OrderDetails od:orderDetails){
            if(od.getOrderId() == orderId){
                finalOd.add(od);
            }
        }
        return finalOd;
    }
    //return all open orders
    public LiveData<List<Order>> getOpenOrders(){
        return orderDao.getOpenOrders(OrderStatus.OPEN.ordinal());
    }

    public void alitest(Order order){
        AsyncResponInt asyncResponInt = new AsyncResponInt() {
            @Override
            public void asyncRes(String string) {

            }
        };
        new InsertOrderAsync(orderDao,asyncResponInt).execute(order);
    }

    //ASYNC TASK

    //update order async task
    private static class UpdateOrderAsync extends AsyncTask<Integer ,Void ,Void> {

        private OrderDao orderDao;
        private OrderDetailsDao orderDetailsDao;
        private UpdateOrderAsync(OrderDao orderDao,OrderDetailsDao orderDetailsDao){
            this.orderDao = orderDao;
            this.orderDetailsDao = orderDetailsDao;
        }

        @Override
        protected Void doInBackground(Integer... orderId) {
            List<OrderDetails> orderDetails = orderDetailsDao.getOrderDetailsByOrderId(orderId[0]).getValue();
            Order order = orderDao.getOrder(orderId[0]);
            int sumBuy=0,sumSell=0,profit;

            for(OrderDetails od : orderDetails){
                sumBuy += od.getBuyPrice();
                sumSell += (od.getSellPrice()*od.getDiscount());
            }
            profit = sumSell-sumBuy;
            order.setProfit(profit);
            order.setAmountBuy(sumBuy);
            order.setAmountSell(sumSell);

            orderDao.update(order);
            return null;
        }
    }
    //delete order details async task
    private static class DeleteOrderDetailAsync extends AsyncTask<OrderDetails ,Void ,Void> {

        private OrderDao orderDao;
        private OrderDetailsDao orderDetailsDao;
        private DeleteOrderDetailAsync(OrderDao orderDao,OrderDetailsDao orderDetailsDao){
            this.orderDao = orderDao;
            this.orderDetailsDao = orderDetailsDao;
        }

        @Override
        protected Void doInBackground(OrderDetails... orderDetails) {
            orderDetailsDao.delete(orderDetails);
            new UpdateOrderAsync(orderDao,orderDetailsDao).execute(orderDetails[0].getOrderId());
            return null;
        }
    }
    //insert order details  and update order async task
    private static class AddOrderDetailToOrderAsync extends AsyncTask<OrderDetails ,Void ,Void> {

        private OrderDao orderDao;
        private OrderDetailsDao orderDetailsDao;
        private AddOrderDetailToOrderAsync(OrderDao orderDao,OrderDetailsDao orderDetailsDao){
            this.orderDao = orderDao;
            this.orderDetailsDao = orderDetailsDao;
        }

        @Override
        protected Void doInBackground(OrderDetails... orderDetails) {
            orderDetailsDao.insert(orderDetails);
            new UpdateOrderAsync(orderDao,orderDetailsDao).execute(orderDetails[0].getOrderId());
            return null;
        }
    }
    //delete order and orderDetails async task
    private static class DeleteOrderAsync extends AsyncTask<Integer ,Void ,Void> {

        private OrderDao orderDao;
        private OrderDetailsDao orderDetailsDao;
        private DeleteOrderAsync(OrderDao orderDao,OrderDetailsDao orderDetailsDao){
            this.orderDao = orderDao;
            this.orderDetailsDao = orderDetailsDao;
        }

        @Override
        protected Void doInBackground(Integer... orderId) {
            OrderDetails[] orderDetails = orderDetailsDao.getOrderDetailsByOrderId(orderId[0]).getValue().toArray(new OrderDetails[0]);
            orderDetailsDao.delete(orderDetails);
            orderDao.delete(orderId[0]);
            return null;
        }
    }
    //insert order with interface for id respond returned
    private static class InsertOrderAsync extends AsyncTask<Order ,Void ,Integer> {

        private OrderDao orderDao;
        private AsyncResponInt asyncResponInt;
        private InsertOrderAsync(OrderDao orderDao,AsyncResponInt asyncResponInt){
            this.orderDao = orderDao;
            this.asyncResponInt = asyncResponInt;
        }

        @Override
        protected Integer doInBackground(Order... orders) {
            Order order = orders[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            //You can change "yyyyMMdd as per your requirement
            int currentDateAndTime = Integer.valueOf(sdf.format(new Date()));

            int id = 0;
            int sumBuy = 0;
            int sumSell = 0;

            order.setAmountBuy(sumBuy);
            order.setAmountSell(sumSell);
            order.setProfit(sumSell-sumBuy);
            order.setCreateDate(currentDateAndTime);
            order.setStatus(OrderStatus.OPEN.ordinal());
            //insert order to local db
            id =(int) orderDao.insert(order);
            return id;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            asyncResponInt.asyncRes(integer.toString());
        }
    }

}
