package com.example.dream_unit.fragment.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.Ram;
import com.example.dream_unit.entity.RamType;
import com.example.dream_unit.fragment.navigation.SearchFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RamDetailsFragment extends Fragment {
    private Ram ram;
    private TextView tvRamCapacity, tvRamSpeed, tvTiming, tvCas, tvType;
    public RamDetailsFragment() {}

    public static RamDetailsFragment newInstance() {
        return new RamDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ram_details, container, false);
        tvRamCapacity=view.findViewById(R.id.tvRamCapacity);
        tvRamSpeed=view.findViewById(R.id.tvRamSpeed);
        tvTiming=view.findViewById(R.id.tvTiming);
        tvCas=view.findViewById(R.id.tvCas);
        tvType=view.findViewById(R.id.tvDetailsRamType);
        tvRamCapacity.setText(String.valueOf(ram.getCapacity()));
        tvRamSpeed.setText(String.valueOf(ram.getSpeed()));
        tvTiming.setText(ram.getTimimg());
        tvCas.setText(String.valueOf(ram.getCas()));
        Call<RamType> call=SearchFragment.api.getRamType(ram.getSpeed());
        call.enqueue(new Callback<RamType>() {
            @Override
            public void onResponse(Call<RamType> call, Response<RamType> response) {
                tvType.setText(response.body().getType());
            }
            @Override
            public void onFailure(Call<RamType> call, Throwable t) {}
        });
        return view;
    }
    public void setRam(Ram ram) {
        this.ram = ram;
    }
}