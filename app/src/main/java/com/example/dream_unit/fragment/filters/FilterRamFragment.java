package com.example.dream_unit.fragment.filters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dream_unit.R;

import java.text.NumberFormat;

public class FilterRamFragment extends Fragment {
    private Spinner spinnerBrand, spinnerCapacity;
    private String[] brands, capacities, ramTypes;
    private ArrayAdapter<String> brandAdapter, capacityAdapter ,ramTypeAdapter;
    private Spinner spinnerRamType;
    private NumberFormat format;
    public FilterRamFragment() {
        format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
    }

    public static FilterRamFragment newInstance(String param1, String param2) {
        return new FilterRamFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_filter_ram, container, false);
        spinnerBrand=view.findViewById(R.id.spinnerRamBrand);
        spinnerCapacity=view.findViewById(R.id.spinnerCapacity);
        spinnerRamType=view.findViewById(R.id.spinnerRamType);
        brands=getResources().getStringArray(R.array.ram_brands);
        capacities=new String[]{"8","16","32","64"};
        ramTypes=getResources().getStringArray(R.array.ram_types_detail);
        brandAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,brands);
        capacityAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,capacities);
        ramTypeAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,ramTypes);
        spinnerBrand.setAdapter(brandAdapter);
        spinnerCapacity.setAdapter(capacityAdapter);
        spinnerRamType.setAdapter(ramTypeAdapter);
        return view;
    }

    public Spinner getSpinnerBrand() {
        return spinnerBrand;
    }

    public Spinner getSpinnerCapacity() {
        return spinnerCapacity;
    }

    public Spinner getSpinnerRamType() {
        return spinnerRamType;
    }
}