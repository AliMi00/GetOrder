package com.example.getorder.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.getorder.R;
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderReport;
import com.example.getorder.viewModel.OrderDailyReportViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDailyReportFragment extends Fragment {

    private OrderDailyReportViewModel mViewModel;
    private RecyclerView recyclerViewReport;
    private OrderDailyReportAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatePickerDialog datePickerDialog;

    public static OrderDailyReportFragment newInstance() {
        OrderDailyReportFragment fragment = new OrderDailyReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order_daily_report_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OrderDailyReportViewModel.class);
        //load recyclerView
        buildRecycleView();
        buildOrder();
        Button btnStar = getView().findViewById(R.id.btnSetStartDate);
        btnStar.setOnClickListener(startDateOnClickListener);

        Button btnEnd = getView().findViewById(R.id.btnSetEndDate);
        btnEnd.setOnClickListener(endDateOnClickListener);

    }

    View.OnClickListener startDateOnClickListener = v -> {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd",Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                Date d = calendar.getTime();
                String c = sdf2.format(d);
                mViewModel.setDate1(Integer.valueOf(c));
                mViewModel.updateReportList();
                buildRecycleView();
                Toast.makeText(getContext(), String.valueOf(c), Toast.LENGTH_SHORT).show();


            }
        },year,month,day);
        datePickerDialog.show();
    };

    View.OnClickListener endDateOnClickListener = v -> {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd",Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,dayOfMonth);
                Date d = calendar.getTime();
                String c = sdf2.format(d);
                mViewModel.setDate2(Integer.valueOf(c));
                mViewModel.updateReportList();
                buildRecycleView();
                Toast.makeText(getContext(), String.valueOf(c), Toast.LENGTH_SHORT).show();


            }
        },year,month,day);
        datePickerDialog.show();
    };

    //build recycleView
    public void buildRecycleView(){
        recyclerViewReport = getView().findViewById(R.id.recyclerViewReport);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerViewReport.setLayoutManager(layoutManager);
        adapter = new OrderDailyReportAdapter();
        //todo set List To Adapter

        mViewModel.getDailyReport().observe(getViewLifecycleOwner(), new Observer<List<OrderReport>>() {
            @Override
            public void onChanged(List<OrderReport> orderReports) {
                if(orderReports == null){
                    orderReports = new ArrayList<>();
                }
                adapter.setOrderReportModelList(orderReports);
            }
        });

        recyclerViewReport.setAdapter(adapter);


    }
    public List<Order> buildOrder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        //You can change "yyyyMMdd as per your requirement
        int currentDateAndTime = Integer.valueOf(sdf.format(new Date()));
        List<Order> dateOrders= new ArrayList<>();
        mViewModel.getOrders().observe(getViewLifecycleOwner(),orders -> {
            for (Order o:orders) {
                if(o.getCreateDate() == currentDateAndTime){
                    dateOrders.add(o);
                }
            }
        });

        return dateOrders;
    }


}
