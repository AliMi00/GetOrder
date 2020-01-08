package com.example.getorder.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.getorder.model.Product;

@Database(entities = {Product.class},version = 1)
public abstract class ProductDb extends RoomDatabase {

    private static ProductDb instance;

    public abstract ProductDao productDao();

    public  static synchronized ProductDb getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProductDb.class,"order_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;

        private PopulateDbAsyncTask(ProductDb db) {
            productDao = db.productDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
