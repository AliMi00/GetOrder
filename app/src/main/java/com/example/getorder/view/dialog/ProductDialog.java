package com.example.getorder.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getorder.R;
import com.example.getorder.model.Product;

public class ProductDialog extends DialogFragment {
    private ProductDialogListener listener;
    public void setListener(ProductDialogListener listener){
        this.listener = listener;
    }

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.product_change_dialog_layout, null);

        final EditText edtTitle,edtDesc,edtSell,edtBuy,edtDis;
        edtTitle = view.findViewById(R.id.edtTitle);
        edtDesc = view.findViewById(R.id.edtDescription);
        edtSell = view.findViewById(R.id.edtSellPrice);
        edtBuy = view.findViewById(R.id.edtBuyPrice);
        edtDis = view.findViewById(R.id.edtDiscount);

        builder.setView(view)
                .setTitle(R.string.product_change_dialog_title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int valu = Integer.valueOf(edtDis.getText().toString());

                        double dis =Double.valueOf(valu) / 100;
                        Product product2 = new Product(
                                edtTitle.getText().toString(),
                                edtDesc.getText().toString(),
                                Integer.valueOf(edtSell.getText().toString()),
                                Integer.valueOf(edtBuy.getText().toString()),
                                dis);

                        product2.setId(product.getId());
                        listener.changeProduct(product2);
                    }
                });
        if(product != null) {
            int a = (int) (product.getDiscount() * 100);
            edtTitle.setText(product.getTitle());
            edtDesc.setText(product.getDescription());
            edtSell.setText(String.valueOf(product.getSellPrice()));
            edtBuy.setText(String.valueOf(product.getBuyPrice()));
            edtDis.setText(String.valueOf(a));
        }
        return builder.create();


    }

    public interface ProductDialogListener{
        void changeProduct(Product product);

    }

}
