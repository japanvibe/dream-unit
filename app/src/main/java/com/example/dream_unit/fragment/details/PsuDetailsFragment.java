package com.example.dream_unit.fragment.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.Psu;

public class PsuDetailsFragment extends Fragment {
    private Psu psu;
    private TextView tvMaxPower, tvFansAmount, tvEnergyEfficiency, tvPsuWeight;
    public PsuDetailsFragment() {}

    public static PsuDetailsFragment newInstance(String param1, String param2) {
        return new PsuDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_psu_details, container, false);
        tvMaxPower=view.findViewById(R.id.tvMaxPower);
        tvFansAmount=view.findViewById(R.id.tvFansAmount);
        tvEnergyEfficiency=view.findViewById(R.id.tvPsuEnergyEfficiency);
        tvPsuWeight=view.findViewById(R.id.tvPsuWeight);
        tvMaxPower.setText(String.valueOf(psu.getMaxPower()));
        tvFansAmount.setText(String.valueOf(psu.getFans()));
        tvEnergyEfficiency.setText(psu.getEnergyEfficiency().toString());
        tvPsuWeight.setText(String.valueOf(psu.getWeight()));
        return view;
    }

    public void setPsu(Psu psu) {
        this.psu = psu;
    }
}