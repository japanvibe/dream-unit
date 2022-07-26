package com.example.dream_unit.fragment.filters;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.example.dream_unit.fragment.bottom_sheet.FilterBottomSheet;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class FilterCpuFragment extends Fragment {
    private Spinner spinnerBrand, spinnerSeries;
    private Slider sliderSpeed;
    private TextView tvCpuSpeedValue;
    private ArrayAdapter<String> brandAdapter;
    private List<ArrayAdapter<String>> arrayAdapterList;
    private String[] brands;
    private String[] intelSeries, AMDSeries;
    private int currentBrandPosition, currentSeriesPosition;
    private NumberFormat format;

    public FilterCpuFragment() {
        arrayAdapterList=new ArrayList<ArrayAdapter<String>>();
        format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(1);
    }

    public static FilterCpuFragment newInstance() {
        return new FilterCpuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_filter_cpu, container, false);
        brands=getResources().getStringArray(R.array.cpu_brands);
        intelSeries=getResources().getStringArray(R.array.intel_series);
        AMDSeries=getResources().getStringArray(R.array.AMD_series);
        brandAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,brands);
        arrayAdapterList.add(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, intelSeries));
        arrayAdapterList.add(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, AMDSeries));
        spinnerBrand=view.findViewById(R.id.spinnerCpuBrand);
        spinnerSeries=view.findViewById(R.id.spinnerCpuSeries);
        spinnerBrand.setAdapter(brandAdapter);
        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=FilterBottomSheet.ALL_ITEMS) {
                    spinnerSeries.setAdapter(arrayAdapterList.get(position-1));
                    spinnerSeries.setEnabled(true);
                    if (currentBrandPosition == position)
                        spinnerSeries.setSelection(currentSeriesPosition);
                    else currentBrandPosition = position;
                }
                else spinnerSeries.setEnabled(false);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spinnerSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentSeriesPosition=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        tvCpuSpeedValue=view.findViewById(R.id.tvCpuSpeedValue);
        sliderSpeed=view.findViewById(R.id.sliderCpuSpeed);
        tvCpuSpeedValue.setText(format.format(sliderSpeed.getValue()));
        sliderSpeed.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                format.setMaximumFractionDigits(1);
                return format.format(value);
            }
        });
        sliderSpeed.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                format.setMaximumFractionDigits(1);
                tvCpuSpeedValue.setText(format.format(value));
            }
        });
        return view;
    }

    public Spinner getSpinnerBrand() {
        return spinnerBrand;
    }

    public Spinner getSpinnerSeries() {
        return spinnerSeries;
    }

    public Slider getSliderSpeed() {return sliderSpeed;}

}