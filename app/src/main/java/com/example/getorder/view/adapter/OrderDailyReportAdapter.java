package com.example.getorder.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.getorder.R;
import com.example.getorder.model.OrderReport;
import java.util.List;

public class OrderDailyReportAdapter extends RecyclerView.Adapter<OrderDailyReportAdapter.OrderDailyReportViewHolder> {

    private List<OrderReport> OrderReportModelList;

    public void setOrderReportModelList(List<OrderReport> orderReportModelList) {
        OrderReportModelList = orderReportModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDailyReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_daily_report_cardview,parent,false);
        OrderDailyReportViewHolder vh = new OrderDailyReportViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDailyReportViewHolder holder, int position) {

        OrderReport currentItem = OrderReportModelList.get(position);
        holder.txtProfit.setText("Profit : "+ String.valueOf(currentItem.getProfit()));
        holder.txtProductQuantity.setText("quantity : "+ String.valueOf(currentItem.getProductQuantity()));
        holder.txtProductSumPrice.setText("Price : " + String.valueOf(currentItem.getProductSum()));
    }

    @Override
    public int getItemCount() {
        if (OrderReportModelList == null) {
            return 0;
        } else {
            return OrderReportModelList.size();
        }
    }

    //view holder class
    public class OrderDailyReportViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProfit;
        public TextView txtProductQuantity;
        public TextView txtProductSumPrice;
        public OrderDailyReportViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProfit = itemView.findViewById(R.id.txtProfit);
            txtProductQuantity = itemView.findViewById(R.id.txtProductQuantity);
            txtProductSumPrice = itemView.findViewById(R.id.txtProductSumPrice);

            //can add click listener

        }
    }
}
