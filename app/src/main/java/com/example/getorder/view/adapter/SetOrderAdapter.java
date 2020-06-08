package com.example.getorder.view.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getorder.R;
import com.example.getorder.model.OrderDetails;
import com.example.getorder.model.Product;

import java.util.ArrayList;
import java.util.List;

public class SetOrderAdapter extends RecyclerView.Adapter<SetOrderAdapter.SetOrderViewHolder> {
    private List<Product> mProductList;
    private List<OrderDetails> mOrderDetailsList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setmProductList(List<Product> mProductList) {
        this.mProductList = mProductList;
        notifyDataSetChanged();
    }

    public void setmOrderDetailsList(List<OrderDetails> mOrderDetailsList) {
        this.mOrderDetailsList = mOrderDetailsList;
        notifyDataSetChanged();
    }

    public class SetOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtProductName;
        public TextView mTxtProductPrice;
        public TextView mTxtQuantity;

        public SetOrderViewHolder(View itemView) {
            super(itemView);
            mTxtProductName = itemView.findViewById(R.id.txtProductName);
            mTxtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            mTxtQuantity = itemView.findViewById(R.id.txtQuantity);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mProductList.get(position));
                    }
                }
            });
        }
    }

    public SetOrderAdapter() {
    }

    @Override
    public SetOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_order_item_cardview, parent, false);
        SetOrderViewHolder evh = new SetOrderViewHolder(v);
        return evh;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(SetOrderViewHolder holder, int position) {
        if(mProductList == null){
            mProductList = new ArrayList<>();
        }
        final Product currentItem = mProductList.get(position);

        holder.mTxtProductName.setText(currentItem.getTitle());
        holder.mTxtProductPrice.setText(String.valueOf( currentItem.getSellPrice()));
        if(mOrderDetailsList.size() == 0){
            holder.mTxtQuantity.setText("0");
        }else {
            for (OrderDetails od : mOrderDetailsList) {
                if (od.getProductId() == currentItem.getId()) {
                    holder.mTxtQuantity.setText(String.valueOf(od.getQuantity()));
                    return;
                }
            }
            holder.mTxtQuantity.setText("0");
        }
    }


    @Override
    public int getItemCount() {
        if(mProductList == null){
            return 0;
        }else {
            return mProductList.size();

        }
    }

    public void SetOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
