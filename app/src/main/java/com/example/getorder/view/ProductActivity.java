package com.example.getorder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.example.getorder.R;
import com.example.getorder.model.Product;
import com.example.getorder.viewModel.ProductViewModel;

import java.util.List;

public class ProductActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProduct().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                //todo update recyle view
                Toast.makeText(ProductActivity.this,"n",Toast.LENGTH_LONG).show();

            }
        });

    }
}
