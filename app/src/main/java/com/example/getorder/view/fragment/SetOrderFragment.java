package com.example.getorder.view.fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
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
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.OrderStatus;
import com.example.getorder.model.Product;
import com.example.getorder.view.dialog.SetOrderDialog;
import com.example.getorder.view.dialog.SetOrderQuantityDialog;
import com.example.getorder.view.adapter.SetOrderAdapter;
import com.example.getorder.viewModel.SetOrderViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SetOrderFragment extends Fragment {

    private static final String ARG_ID = "argOrderId";

    private SetOrderViewModel mViewModel;

    private RecyclerView rvSetOrder;
    private SetOrderAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Order mOrder;
    private boolean returnState = false;


    public static SetOrderFragment newInstance(int orderId) {
        SetOrderFragment fragment = new SetOrderFragment();
        Bundle arg = new Bundle();
        arg.putInt(ARG_ID,orderId);
        fragment.setArguments(arg);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        if(getArguments() != null){
            mViewModel.setOrderId(getArguments().getInt(ARG_ID));
        }

        FloatingActionButton btnAddOrder = getView().findViewById(R.id.btnAddOrder);
        btnAddOrder.setOnClickListener(btnAddOrderOnClickListener);
        buildRecycleView();
        mViewModel.deleteNewOrders();
        if(mViewModel.getOrderId() != 0 ){
            mViewModel.getOrderById(mViewModel.getOrderId()).observe(getViewLifecycleOwner(),order -> {
                if(order != null){
                    mOrder = order;
                    getTempOrderDetails();
                    returnState = true;
                }
            });

        }else{

            mViewModel.getLastOrder().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
                @Override
                public void onChanged(List<Order> orders) {
                    if(orders.size()>0){
                        mOrder =orders.get(0);
                        getTempOrderDetails();
                    }
                }
            });
        }
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
                if(mOrder == null){
                    openSetOrderDialog();
                }
                else {
                    openDialog(product);
                }

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
            if(mOrder == null){
                openSetOrderDialog();
            }
            else {
                openDialogForSubmitOrder();
            }

        }
    };

    public void openDialog(final Product product) {
        SetOrderQuantityDialog dialog = new SetOrderQuantityDialog();
        dialog.setOrderQuantityListener(new SetOrderQuantityDialog.SetOrderQuantityListener() {
            @Override
            public void getQuantity(int quantity,String description) {
                List<OrderDetails> od = mViewModel.getTempOrderDetailsList();
                for(OrderDetails o : od){
                    if(o.getProductId() == product.getId()){
                        o.setQuantity(quantity);
                        o.setDescription(description);
                        mViewModel.newUpdateOrderDetails(o);
                        getTempOrderDetails();
                        return;
                    }
                }
                mViewModel.newAddOrderDetails(new OrderDetails(
                        mOrder.getId(),
                        product.getId(),
                        description,
                        product.getSellPrice(),
                        product.getBuyPrice(),
                        quantity,
                        product.getDiscount()));
            }
        });
        dialog.show(getActivity().getSupportFragmentManager(),"quantity");

    }

    public void openSetOrderDialog(){
        SetOrderDialog dialog = new SetOrderDialog();
        dialog.setListener(new SetOrderDialog.SetOrderDialogListener() {
            @Override
            public void setOrder(Order order) {
                if(order != null && mViewModel.getTempOrderDetailsList() != null) {
                    int id = mViewModel.newAddOrder(order);
                    Toast.makeText(getContext(), "order Added" + String.valueOf(id), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show(getActivity().getSupportFragmentManager(),"setOrder");
    }

    // dialog to submit the order and take new order
    public void openDialogForSubmitOrder(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.set_order_submit_title)
                .setMessage(R.string.set_order_submit_dialog_message)
                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOrder.setStatus(OrderStatus.OPEN.ordinal());
                        mViewModel.newUpdateOrder(mViewModel.getTempOrderDetailsList(),mOrder);
                        mOrder = null;
                        mViewModel.setTempOrderDetailsList(new ArrayList<>());
                        buildRecycleView();
                        if(returnState){
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container,new WaitingFragment())
                                    .commit();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void getTempOrderDetails(){
        if(mOrder!=null){
            mViewModel.getAllNewOrderDetails(mOrder.getId()).observe(getViewLifecycleOwner(), new Observer<List<OrderDetails>>() {
                @Override
                public void onChanged(List<OrderDetails> orderDetails) {
                    mViewModel.setTempOrderDetailsList(orderDetails);
                    adapter.setmOrderDetailsList(orderDetails);

                }
            });
        }
    }

}

