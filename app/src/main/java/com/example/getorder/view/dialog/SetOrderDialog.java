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
import com.example.getorder.model.Order;
import com.example.getorder.model.OrderStatus;

public class SetOrderDialog extends DialogFragment {
    private SetOrderDialogListener listener;
    private EditText edtTableNum;

    public void setListener(SetOrderDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder((getActivity()));

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.set_order_dialog_layout,null);

        edtTableNum = view.findViewById(R.id.edtTableNum);

        builder.setView(view)
                .setTitle(R.string.set_order_dialog_title)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Order order = new Order();
                        order.setTableNum(Integer.valueOf(edtTableNum.getText().toString()));
                        order.setStatus(OrderStatus.NEW.ordinal());
                        listener.setOrder(order);
                    }
                });

        return builder.create();
    }

    public interface SetOrderDialogListener{
        void setOrder(Order order);
    }
}
