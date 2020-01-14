package com.example.getorder.db;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.Product;

@Database(entities = {OrderDetails.class, Order.class, Product.class},version = 1)
public abstract class OrderDb extends RoomDatabase {
    //static instance of db
    private static OrderDb instance;

    public abstract ProductDao productDao();
    public abstract OrderDetailsDao orderDetailsDao();
    public abstract OrderDao orderDao();

    public  static synchronized OrderDb getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    OrderDb.class,"order_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new OrderDb.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;
        private OrderDetailsDao orderDetailsDao;
        private OrderDao orderDao;

        private PopulateDbAsyncTask(OrderDb db) {
            productDao = db.productDao();
            orderDetailsDao = db.orderDetailsDao();
            orderDao =db.orderDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
