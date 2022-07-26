package com.example.dream_unit.fragment.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.Mobo;


public class MoboDetailsFragment extends Fragment {
    private Mobo mobo;
    private TextView tvSocket, tvChipset, tvRamCapacity, tvRamType, tvUsbAmount, tvPciAmount, tvType;
    public MoboDetailsFragment() {}

    public static MoboDetailsFragment newInstance(String param1, String param2) {
        return new MoboDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_mobo_details, container, false);
        tvSocket=view.findViewById(R.id.tvMoboSocket);
        tvChipset=view.findViewById(R.id.tvChipset);
        tvRamCapacity=view.findViewById(R.id.tvMoboRamCapacity);
        tvRamType=view.findViewById(R.id.tvMoboRamType);
        tvUsbAmount=view.findViewById(R.id.tvMoboUsbAmount);
        tvPciAmount=view.findViewById(R.id.tvPciAmount);
        tvType=view.findViewById(R.id.tvMoboType);
        tvSocket.setText(mobo.getSocket());
        tvChipset.setText(mobo.getChipset());
        tvRamCapacity.setText(String.valueOf(mobo.getMemoryCap()));
        tvRamType.setText(mobo.getMemoryStandard().toString());
        tvUsbAmount.setText(String.valueOf(mobo.getUsbPorts()));
        tvPciAmount.setText(String.valueOf(mobo.getPci()));
        tvType.setText(mobo.getType().toString());
        return view;
    }

    public void setMobo(Mobo mobo) {
        this.mobo = mobo;
    }
}