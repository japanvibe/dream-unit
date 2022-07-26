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
import com.google.android.material.slider.Slider;

import java.text.NumberFormat;

public class FilterVideoCardFragment extends Fragment {
    private Spinner spinnerBrand;
    private Slider sliderMemory;
    private TextView tvMemory;
    private ArrayAdapter<String> brandAdapter;
    private String[] brands;
    private NumberFormat format;
    public FilterVideoCardFragment() {
        format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
    }

    public static FilterVideoCardFragment newInstance(String param1, String param2) {
        return new FilterVideoCardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_filter_video_card, container, false);
         spinnerBrand=view.findViewById(R.id.spinnerVideoCardBrand);
         sliderMemory=view.findViewById(R.id.sliderVideoCardMemory);
         tvMemory=view.findViewById(R.id.tvMemory);
         tvMemory.setText(format.format(sliderMemory.getValue()));
         sliderMemory.addOnChangeListener(new Slider.OnChangeListener() {
             @SuppressLint("RestrictedApi")
             @Override
             public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                 NumberFormat format=NumberFormat.getNumberInstance();
                 tvMemory.setText(format.format(value));
             }
         });
         brands=getResources().getStringArray(R.array.video_card_brands);
         brandAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,brands);
         spinnerBrand.setAdapter(brandAdapter);
         return view;
    }

    public Spinner getSpinnerBrand() {return spinnerBrand;}

    public Slider getSliderMemory() {return sliderMemory;}
}