package com.example.getorder.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getorder.R;

public class SetOrderQuantityDialog extends DialogFragment {

    private NumberPicker numPicker;
    private EditText edtDesc;

    public interface SetOrderQuantityListener{
        void getQuantity(int quantity,String description);
    }

    private SetOrderQuantityListener listener;

    public void setOrderQuantityListener(SetOrderQuantityListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.set_order_quantity_dialog, null);
        numPicker = view.findViewById(R.id.numPicker);
        edtDesc = view.findViewById(R.id.edtDescription);
        numPicker.setMinValue(0);
        numPicker.setMaxValue(20);
        builder.setView(view)
                .setTitle(R.string.set_quantity_dialog_title)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        listener.getQuantity(numPicker.getValue(),edtDesc.getText().toString());
                    }
                });


        return builder.create();
    }
}
