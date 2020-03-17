package com.example.getorder.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getorder.R;
import com.example.getorder.model.Order;

import java.util.List;

public class WaitingAdapter extends RecyclerView.Adapter<WaitingAdapter.WaitingViewHolder> {
    private List<Order> mOrderList;

    private OnItemClickListener listener;

    public void setmOrderList(List<Order> mOrderList) {
        this.mOrderList = mOrderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaitingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.waiting_item_cardview, parent, false);
        WaitingViewHolder evh = new WaitingViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingViewHolder holder, int position) {
        Order currentItem = mOrderList.get(position);

        holder.txtTable.setText("Table : " + currentItem.getTableNum());
        holder.txtTotalPrice.setText(String.valueOf(currentItem.getAmountSell()));


    }

    @Override
    public int getItemCount() {
        if (mOrderList == null) {
            return 0;
        } else {
            return mOrderList.size();
        }
    }

    public interface OnItemClickListener {
        void onOrderClick(Order order);
        void onShowItemClick(Order order);
    }

    //View Holder Class
    public class WaitingViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTable;
        public TextView txtTotalPrice;
        public ImageButton btnOpenItems;

        public WaitingViewHolder(View itemView) {
            super(itemView);
            txtTable = itemView.findViewById(R.id.txtTable);
            txtTotalPrice = itemView.findViewById(R.id.txtOrderTotalPrice);
            btnOpenItems = itemView.findViewById(R.id.btnOpenItems);

            btnOpenItems.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if(listener !=null && position != RecyclerView.NO_POSITION){
                    listener.onShowItemClick(mOrderList.get(position));
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onOrderClick(mOrderList.get(position));
                    }
                }
            });



        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
