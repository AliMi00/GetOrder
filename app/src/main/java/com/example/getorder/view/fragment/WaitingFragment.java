package com.example.getorder.view.fragment;

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
import android.widget.Toast;

import com.example.getorder.R;
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderStatus;
import com.example.getorder.view.dialog.WaitingDialog;
import com.example.getorder.view.adapter.WaitingAdapter;
import com.example.getorder.viewModel.WaitingViewModel;

import java.util.ArrayList;
import java.util.List;

public class WaitingFragment extends Fragment {

    private WaitingViewModel mViewModel;
    private RecyclerView rvWaiting;
    private WaitingAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public static WaitingFragment newInstance() {
        return new WaitingFragment();
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

        buildRecycleView();
    }


    //build recycleView And click Listener
    public void buildRecycleView(){
        rvWaiting = getView().findViewById(R.id.rvWaiting);
        layoutManager = new LinearLayoutManager(getContext());
        rvWaiting.setLayoutManager(layoutManager);
        adapter = new WaitingAdapter();
        //click on each item open dialog box
        adapter.setOnItemClickListener(new WaitingAdapter.OnItemClickListener() {
            @Override
            public void onOrderClick(Order order) {
                openDialog(order);
            }

            @Override
            public void onShowItemClick(Order order) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,SetOrderFragment.newInstance(order.getId())).addToBackStack(null).commit();
                Toast.makeText(getContext(), "aaaaa", Toast.LENGTH_SHORT).show();

            }
        });
        //get all the open orders this is live data
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
    //open dialog box to set order to paid and close if click ok setPaidOrder set the order status to paid
    public void openDialog(final Order order) {
        WaitingDialog waitingDialog = new WaitingDialog();
        WaitingDialog.WaitingDialogListener listener = new WaitingDialog.WaitingDialogListener() {
            @Override
            public void setPaidOrder(OrderStatus orderStatus) {
                order.setStatus(orderStatus.ordinal());
                mViewModel.setOrderStatus(order);

            }
        };
        waitingDialog.setListener(listener);
        waitingDialog.show(getActivity().getSupportFragmentManager(),"ali");
    }

}
