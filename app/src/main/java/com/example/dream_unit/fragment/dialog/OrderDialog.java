package com.example.dream_unit.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.adapter.GoodsListAdapter;
import com.example.dream_unit.entity.Order;
import com.example.dream_unit.entity.Part;
import com.example.dream_unit.fragment.navigation.SearchFragment;

import retrofit2.Call;
import retrofit2.Response;

public class OrderDialog extends DialogFragment {
    private Part part;
    private Context context;
    private int partType;

    public OrderDialog(Context context, Part part, int partType) {
        this.context=context;
        this.part=part;
        this.partType=partType;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=getLayoutInflater().inflate(R.layout.order_dialog_layout,null);
        NumberPicker numberPicker=view.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        Order order=new Order();
        order.setAmount(numberPicker.getValue());
        TextView tvOrderSum=view.findViewById(R.id.tvOrderSum);
        tvOrderSum.setText(String.valueOf(Math.round(part.getPrice()*SearchFragment.EXCHANGE_RATE)));
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvOrderSum.setText(String.valueOf(Math.round(part.getPrice()*SearchFragment.EXCHANGE_RATE)*newVal));
                order.setAmount(numberPicker.getValue());
            }
        });
        builder.setView(view);
        builder.setTitle(R.string.amount_choice);
        builder.setPositiveButton(R.string.order, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                order.setUserId(InitialActivity.userId);
                order.setGoods(part.getBrand().toString()+" "+part.getName());
                order.setGoodsType(partType);
                order.setSum(Math.round(part.getPrice()*SearchFragment.EXCHANGE_RATE)*numberPicker.getValue());
                Call<Integer> callOrder=SearchFragment.api.orderGood(order);
                sendCallOrder(callOrder);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
        });
        return builder.create();
    }

    private void sendCallOrder(Call<Integer> call){
        if(InitialActivity.checkConnection()){
            call.enqueue(new retrofit2.Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> callAddToFavorite, Response<Integer> response) {
                    Toast.makeText(context, R.string.order_good_message, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Integer> callAddToFavorite, Throwable t) {}
            });
        }
        else Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

}
