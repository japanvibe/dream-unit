package com.example.dream_unit.fragment.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.Cpu;

public class CpuDetailsFragment extends Fragment {
    private Cpu cpu;
    private TextView tvSeries, tvSpeed, tvCore, tvThread, tvTurbo, tvTdp, tvSocket, tvGen;
    public CpuDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_cpu_details, container, false);
       tvSeries=view.findViewById(R.id.tvCpuSeries);
       tvSpeed=view.findViewById(R.id.tvCpuSpeed);
       tvCore=view.findViewById(R.id.tvCores);
       tvThread=view.findViewById(R.id.tvThreads);
       tvTurbo=view.findViewById(R.id.tvTurbo);
       tvTdp=view.findViewById(R.id.tvTdp);
       tvSocket=view.findViewById(R.id.tvSocket);
       tvGen=view.findViewById(R.id.tvGen);
       tvSeries.setText(cpu.getSeries().toString());
       tvSpeed.setText(String.valueOf(cpu.getSpeed()));
       tvCore.setText(String.valueOf(cpu.getCore()));
       tvThread.setText(String.valueOf(cpu.getThread()));
       tvTurbo.setText(String.valueOf(cpu.getThread()));
       tvTdp.setText(String.valueOf(cpu.getTdp()));
       tvSocket.setText(cpu.getSocket());
       tvGen.setText(cpu.getGen());
       return  view;
    }
    public void setCpu(Cpu cpu){
        this.cpu=cpu;
    }
}