package com.example.getorder.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getorder.R;
import com.example.getorder.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> mProductList = new ArrayList<>();
    private OnItemClickListener listener;

    public void setmProductList(List<Product> mProductList) {
        this.mProductList = mProductList;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView mTxtProductName;
        public TextView mTxtProductPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mTxtProductName = itemView.findViewById(R.id.txtProductName);
            mTxtProductPrice = itemView.findViewById(R.id.txtProductPrice);

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

    public ProductAdapter() {
    }

    public Product getProductAt(int position) {
        return mProductList.get(position);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_cardview, parent, false);
        ProductViewHolder evh = new ProductViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Product currentItem = mProductList.get(position);
        holder.mTxtProductName.setText(currentItem.getTitle());
        holder.mTxtProductPrice.setText(String.valueOf(currentItem.getSellPrice()));
    }

    @Override
    public int getItemCount() {
        if (mProductList == null) {
            return 0;
        } else {
            return mProductList.size();

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void SetOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}