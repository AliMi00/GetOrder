package com.example.getorder.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.getorder.R;
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.Product;
import com.example.getorder.viewModel.SetOrderViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SetOrderFragment extends Fragment {

    private SetOrderViewModel mViewModel;

    private RecyclerView rvSetOrder;
    private SetOrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public static SetOrderFragment newInstance() {
        return new SetOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.set_order_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SetOrderViewModel.class);
        // TODO: Use the ViewModel


        FloatingActionButton btnAddOrder = getView().findViewById(R.id.btnAddOrder);
        btnAddOrder.setOnClickListener(btnAddOrderOnClickListener);
        buildRecycleView();

    }

    //build recycleView And click Listener
    public void buildRecycleView(){
        rvSetOrder = getView().findViewById(R.id.rvSetOrder);
        layoutManager = new LinearLayoutManager(getContext());
        rvSetOrder.setLayoutManager(layoutManager);
        adapter = new SetOrderAdapter();
        adapter.SetOnItemClickListener(new SetOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                //todo set on item click to open dialog bar to set quantity and add order details


            }
        });
        mViewModel.getAllProduct().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                adapter.setmProductList(products);
            }
        });
        adapter.setmOrderDetailsList(mViewModel.getTempOrderDetailsList());
        rvSetOrder.setAdapter(adapter);

    }

    //Click Listener of FloatActionBtn
    private View.OnClickListener btnAddOrderOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todo goto add Order or open dialog box

        }
    };

}

