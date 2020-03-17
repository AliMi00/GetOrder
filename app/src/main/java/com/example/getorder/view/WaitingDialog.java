package com.example.getorder.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getorder.R;
import com.example.getorder.model.OrderStatus;
import com.example.getorder.services.OrdersServices;

public class WaitingDialog extends DialogFragment {

    private RadioButton rbtnPaid,rbtnUnPaid;


    WaitingDialogListener listener;

    public void setListener(WaitingDialogListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.waiting_dialog_layout, null);

        builder.setView(view)
                .setTitle(R.string.waiting_dialog_title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(rbtnPaid.isChecked()){
                            listener.setPaidOrder(OrderStatus.PAYED);
                        }
                        else if(rbtnUnPaid.isChecked()){
                            listener.setPaidOrder(OrderStatus.UNPAYED);
                        }
                    }
                });

        rbtnPaid = view.findViewById(R.id.rbtnPaid);
        rbtnUnPaid = view.findViewById(R.id.rbtnUnPaid);

        return builder.create();
    }

    public interface WaitingDialogListener{
        void setPaidOrder(OrderStatus orderStatus);
    }
}
