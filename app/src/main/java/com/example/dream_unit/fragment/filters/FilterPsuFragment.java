package com.example.dream_unit.fragment.filters;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;

public class FilterPsuFragment extends Fragment {
    private Spinner spinnerBrand, spinnerEnergyEfficiency;
    private String[] brands, energyEfficiencies;
    private ArrayAdapter<String> brandAdapter, energyEfficiencyAdapter;
    private RangeSlider sliderPsuPower;
    private TextView tvPsuPowerFrom, tvPsuPowerTo;
    private NumberFormat format;
    public FilterPsuFragment() {
        format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
    }
    public static FilterPsuFragment newInstance(String param1, String param2) {
        return new FilterPsuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_filter_psu, container, false);
        spinnerBrand=view.findViewById(R.id.spinnerPsuBrand);
        spinnerEnergyEfficiency=view.findViewById(R.id.spinnerEnergyEfficiency);
        brands=getResources().getStringArray(R.array.psu_brands);
        energyEfficiencies=getResources().getStringArray(R.array.psu_energy_efficiencies);
        brandAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,brands);
        energyEfficiencyAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,energyEfficiencies);
        spinnerBrand.setAdapter(brandAdapter);
        spinnerEnergyEfficiency.setAdapter(energyEfficiencyAdapter);
        tvPsuPowerFrom=view.findViewById(R.id.tvPsuPowerFrom);
        tvPsuPowerTo=view.findViewById(R.id.tvPsuPowerTo);
        sliderPsuPower=view.findViewById(R.id.sliderPsuPower);
        setPower();
        sliderPsuPower.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                setPower();
            }
        });
        sliderPsuPower.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return format.format(value);
            }
        });
        return view;
    }
    private void setPower(){
        tvPsuPowerFrom.setText(format.format(sliderPsuPower.getValues().get(0)));
        tvPsuPowerTo.setText(format.format(sliderPsuPower.getValues().get(1)));
    }

    public Spinner getSpinnerBrand() {
        return spinnerBrand;
    }

    public Spinner getSpinnerEnergyEfficiency() {
        return spinnerEnergyEfficiency;
    }

    public RangeSlider getSliderPsuPower() {
        return sliderPsuPower;
    }
}