package com.example.dream_unit.fragment.filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dream_unit.R;

import java.text.NumberFormat;

public class FilterMoboFragment extends Fragment {
    private Spinner spinnerBrand, spinnerMoboType, spinnerRamCapacity, spinnerRamType;
    private String[] brands, moboTypes, ramCapacities, ramTypes;
    private ArrayAdapter<String> brandAdapter, moboTypeAdapter, ramCapacityAdapter, ramTypeAdapter;
    private TextView tvUsbAmount;
    private NumberFormat format;
    public FilterMoboFragment() {
        format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
    }

    public static FilterMoboFragment newInstance(String param1, String param2) {
        return new FilterMoboFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_filter_mobo, container, false);
        spinnerBrand=view.findViewById(R.id.spinnerMoboBrand);
        spinnerMoboType=view.findViewById(R.id.spinnerMoboType);
        spinnerRamCapacity=view.findViewById(R.id.spinnerRamCapacity);
        spinnerRamType=view.findViewById(R.id.spinnerRamType);
        brands=getResources().getStringArray(R.array.mobo_brands);
        moboTypes=getResources().getStringArray(R.array.cpu_brands);
        ramCapacities=new String[]{"32","64","128"};
        ramTypes=getResources().getStringArray(R.array.ram_types);

        brandAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, brands);
        moboTypeAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, moboTypes);
        ramCapacityAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,ramCapacities);
        ramTypeAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ramTypes);

        spinnerBrand.setAdapter(brandAdapter);
        spinnerMoboType.setAdapter(moboTypeAdapter);
        spinnerRamCapacity.setAdapter(ramCapacityAdapter);
        spinnerRamType.setAdapter(ramTypeAdapter);

        return view;
    }

    public Spinner getSpinnerBrand() {
        return spinnerBrand;
    }

    public Spinner getSpinnerMoboType() {
        return spinnerMoboType;
    }

    public Spinner getSpinnerRamCapacity() {
        return spinnerRamCapacity;
    }

    public Spinner getSpinnerRamType() {
        return spinnerRamType;
    }

}