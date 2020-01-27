package com.example.getorder.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.getorder.R;
import com.example.getorder.model.Product;
import com.example.getorder.viewModel.ProductsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private ProductsViewModel mViewModel;
    private RecyclerView rvProduct;
    private ProductAdapter productAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.products_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductsViewModel.class);

        FloatingActionButton btnAddProduct = getView().findViewById(R.id.btnAddProduct);
        btnAddProduct.setOnClickListener(btnAddProductOnClickListener);
        buildRecycleView();


    }
    //build recycleView And click Listener
    public void buildRecycleView(){
        rvProduct = getView().findViewById(R.id.rvProduct);
        mLayoutManager = new LinearLayoutManager(getContext());
        rvProduct.setLayoutManager(mLayoutManager);
        productAdapter = new ProductAdapter();
        productAdapter.SetOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                //todo click on each item of list

            }
        });
        rvProduct.setAdapter(productAdapter);
        mViewModel.getAllProduct().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.setmProductList(products);
            }
        });
    }

    //Click Listener of FloatActionBtn
    View.OnClickListener btnAddProductOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo goto add product activity

        }
    };
}


