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

import com.example.getorder.R;
import com.example.getorder.model.Order;
import com.example.getorder.model.Product;
import com.example.getorder.viewModel.WaitingViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatingFragment extends Fragment {

    private WaitingViewModel mViewModel;
    private RecyclerView rvWaiting;
    private WaitingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static WatingFragment newInstance() {
        return new WatingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wating_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(WaitingViewModel.class);
        // TODO: Use the ViewModel
        buildRecycleView();
    }


    //build recycleView And click Listener
    public void buildRecycleView(){
        rvWaiting = getView().findViewById(R.id.rvWaiting);
        layoutManager = new LinearLayoutManager(getContext());
        rvWaiting.setLayoutManager(layoutManager);
        adapter = new WaitingAdapter();
        adapter.setOnItemClickListener(new WaitingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                //todo Click on each item


            }
        });
        mViewModel.getAllOpenOrders().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                if(orders == null){
                    orders = new ArrayList<>();
                }
                adapter.setmOrderList(orders);
            }
        });
        rvWaiting.setAdapter(adapter);

    }

}
