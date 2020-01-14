package com.example.getorder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.getorder.R;
import com.example.getorder.db.OrderDb;
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderStatus;

public class MainActivity extends AppCompatActivity {

    Button test_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test_btn = findViewById(R.id.test_btn);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderStatus orderStatus = OrderStatus.OPEN;
                String s =String.valueOf(orderStatus.ordinal());
                Toast.makeText(MainActivity.this, s , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
