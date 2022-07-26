package com.example.dream_unit.fragment.bottom_sheet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.entity.Cpu;
import com.example.dream_unit.entity.Mobo;
import com.example.dream_unit.entity.Psu;
import com.example.dream_unit.entity.Ram;
import com.example.dream_unit.entity.VideoCard;
import com.example.dream_unit.fragment.filters.FilterCpuFragment;
import com.example.dream_unit.fragment.filters.FilterMoboFragment;
import com.example.dream_unit.fragment.filters.FilterPsuFragment;
import com.example.dream_unit.fragment.filters.FilterRamFragment;
import com.example.dream_unit.fragment.filters.FilterVideoCardFragment;
import com.example.dream_unit.fragment.navigation.*;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.Part;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterBottomSheet extends BottomSheetDialogFragment {
    private int filterType;
    private View view;
    private RangeSlider sliderPrice;
    private TextView tvPriceFrom, tvPriceTo;
    private Button btnFind;
    private ImageView ivCategoryIcon;
    private TextView tvFiltersTitle;
    private FilterCpuFragment filterCpuFragment;
    private FilterVideoCardFragment filterVideoCardFragment;
    private FilterMoboFragment filterMoboFragment;
    private FilterRamFragment filterRamFragment;
    private FilterPsuFragment filterPsuFragment;
    private NumberFormat format;
    public static final int ALL_ITEMS=0;
    private final boolean ALL=false;
    public FilterBottomSheet() {
        format=NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(0);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.bottom_sheet_filters,container,false);
        ivCategoryIcon=view.findViewById(R.id.ivCategoryIcon);
        tvFiltersTitle=view.findViewById(R.id.tvFiltersTitle);
        sliderPrice=view.findViewById(R.id.sliderPrice);
        tvPriceFrom=view.findViewById(R.id.tvPriceFrom);
        tvPriceTo=view.findViewById(R.id.tvPriceTo);
        tvPriceFrom.setText(format.format(sliderPrice.getValues().get(0)));
        tvPriceTo.setText(format.format(sliderPrice.getValues().get(1)));
        sliderPrice.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {
                return format.format(value);
            }
        });
        sliderPrice.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                format.setMaximumFractionDigits(0);
                tvPriceFrom.setText(format.format(slider.getValues().get(0)));
                tvPriceTo.setText(format.format(slider.getValues().get(1)));
            }
        });
        btnFind=view.findViewById(R.id.btnFind);
        switch (filterType){
            case SearchFragment.CPU:
                Cpu filterCpu=new Cpu();
                if(filterCpuFragment==null)filterCpuFragment=new FilterCpuFragment();
                ivCategoryIcon.setImageResource(R.drawable.cpu);
                tvFiltersTitle.setText(R.string.cpu);
                getChildFragmentManager().beginTransaction().replace(R.id.layoutFiltersContainer,filterCpuFragment,null).commit();
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(filterCpuFragment.getSpinnerBrand().getSelectedItemPosition()==ALL_ITEMS)
                        {
                            filterCpu.setBrand(ALL);
                            filterCpu.setSeries(ALL);
                        }
                        else {
                            filterCpu.setBrand(filterCpuFragment.getSpinnerBrand().getSelectedItem().toString());
                            if(filterCpuFragment.getSpinnerSeries().getSelectedItemPosition()!=ALL_ITEMS)
                                filterCpu.setSeries(filterCpuFragment.getSpinnerSeries().getSelectedItem().toString());
                            else filterCpu.setSeries(ALL);
                        }
                        filterCpu.setSpeed(filterCpuFragment.getSliderSpeed().getValue());
                        setPrice(filterCpu);
                        sendCall(filterType,filterCpu);
                    }
                });
                break;
            case SearchFragment.VIDEO_CARD:
                VideoCard filterVideoCard=new VideoCard();
                if(filterVideoCardFragment==null)filterVideoCardFragment=new FilterVideoCardFragment();
                ivCategoryIcon.setImageResource(R.drawable.graphics_card);
                tvFiltersTitle.setText(R.string.video_card);
                getChildFragmentManager().beginTransaction().replace(R.id.layoutFiltersContainer,filterVideoCardFragment,null).commit();
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(filterVideoCardFragment.getSpinnerBrand().getSelectedItemPosition()==ALL_ITEMS)
                            filterVideoCard.setBrand(ALL);
                        else filterVideoCard.setBrand(filterVideoCardFragment.getSpinnerBrand().getSelectedItem().toString());
                        filterVideoCard.setMemory((int)filterVideoCardFragment.getSliderMemory().getValue());
                        setPrice(filterVideoCard);
                        sendCall(filterType, filterVideoCard);
                    }
                });
                break;
                case SearchFragment.MOBO:
                    Mobo filterMobo=new Mobo();
                    if(filterMoboFragment==null)filterMoboFragment=new FilterMoboFragment();
                    ivCategoryIcon.setImageResource(R.drawable.motherboard);
                    tvFiltersTitle.setText(R.string.mobo);
                    getChildFragmentManager().beginTransaction().replace(R.id.layoutFiltersContainer,filterMoboFragment,null).commit();
                    btnFind.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(filterMoboFragment.getSpinnerBrand().getSelectedItemPosition()==ALL_ITEMS)
                                filterMobo.setBrand(ALL);
                            else filterMobo.setBrand(filterMoboFragment.getSpinnerBrand().getSelectedItem().toString());
                            if(filterMoboFragment.getSpinnerMoboType().getSelectedItemPosition()==ALL_ITEMS)
                                filterMobo.setType(ALL);
                            else filterMobo.setType(filterMoboFragment.getSpinnerMoboType().getSelectedItem().toString());
                            filterMobo.setMemoryCap(Integer.parseInt(filterMoboFragment.getSpinnerRamCapacity().getSelectedItem().toString()));
                            if(filterMoboFragment.getSpinnerRamType().getSelectedItemPosition()==ALL_ITEMS)
                                filterMobo.setMemoryStandard(ALL);
                            else filterMobo.setMemoryStandard(filterMoboFragment.getSpinnerRamType().getSelectedItem().toString());
                            setPrice(filterMobo);
                            sendCall(filterType, filterMobo);
                        }
                    });
                    break;
            case SearchFragment.RAM:
                Ram filterRam=new Ram();
                if(filterRamFragment==null)filterRamFragment=new FilterRamFragment();
                ivCategoryIcon.setImageResource(R.drawable.memory);
                tvFiltersTitle.setText(R.string.ram);
                getChildFragmentManager().beginTransaction().replace(R.id.layoutFiltersContainer,filterRamFragment,null).commit();
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(filterRamFragment.getSpinnerBrand().getSelectedItemPosition()==ALL_ITEMS)
                            filterRam.setBrand(ALL);
                        else filterRam.setBrand(filterRamFragment.getSpinnerBrand().getSelectedItem().toString());
                        filterRam.setCapacity(Integer.parseInt(filterRamFragment.getSpinnerCapacity().getSelectedItem().toString()));
                        if(filterRamFragment.getSpinnerRamType().getSelectedItemPosition()==ALL_ITEMS)
                            filterRam.setRamType(ALL);
                        else filterRam.setRamType(filterRamFragment.getSpinnerRamType().getSelectedItem());
                        System.out.println(filterRam.getRamType());
                        setPrice(filterRam);
                        sendCall(filterType,filterRam);
                    }
                });
                break;
            case SearchFragment.PSU:
                Psu filterPsu=new Psu();
                if(filterPsuFragment==null)filterPsuFragment=new FilterPsuFragment();
                ivCategoryIcon.setImageResource(R.drawable.power_supply);
                tvFiltersTitle.setText(R.string.psu);
                getChildFragmentManager().beginTransaction().replace(R.id.layoutFiltersContainer,filterPsuFragment,null).commit();
                btnFind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(filterPsuFragment.getSpinnerBrand().getSelectedItemPosition()==ALL_ITEMS)
                            filterPsu.setBrand(ALL);
                        else filterPsu.setBrand(filterPsuFragment.getSpinnerBrand().getSelectedItem().toString());
                        if(filterPsuFragment.getSpinnerEnergyEfficiency().getSelectedItemPosition()==ALL_ITEMS)
                            filterPsu.setEnergyEfficiency(ALL);
                        else filterPsu.setEnergyEfficiency(filterPsuFragment.getSpinnerEnergyEfficiency().getSelectedItem().toString());
                        filterPsu.setPowerFrom(Math.round(filterPsuFragment.getSliderPsuPower().getValues().get(0)));
                        filterPsu.setPowerTo(Math.round(filterPsuFragment.getSliderPsuPower().getValues().get(1)));
                        setPrice(filterPsu);
                        sendCall(filterType,filterPsu);
                    }
                });
                break;
        }
        return view;
    }

    private void sendCall(int filterType, Part filter){
        Call call = null;
        switch (filterType){
            case SearchFragment.CPU:
                call=SearchFragment.api.filterCpu((Cpu)filter);
                break;
            case SearchFragment.VIDEO_CARD:
                call=SearchFragment.api.filterVideoCard((VideoCard)filter);
                break;
            case SearchFragment.MOBO:
                call=SearchFragment.api.filterMobo((Mobo)filter);
                break;
            case SearchFragment.RAM:
                call=SearchFragment.api.filterRam((Ram)filter);
                break;
            case SearchFragment.PSU:
                call=SearchFragment.api.filterPsu((Psu)filter);
                break;
        }
        if(InitialActivity.checkConnection()){
            call.enqueue(new Callback<List<Part>>() {
                @Override
                public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
                    SearchFragment.layoutManager.scrollToPosition(0);
                    SearchFragment.goodsListAdapter.setItems(response.body());
                    switch (SearchFragment.chipGroup.getCheckedChipId()){
                        case R.id.chipPriceAsc:
                            SearchFragment.goodsListAdapter.sortByPriceAsc();
                            break;
                        case R.id.chipPriceDesc:
                            SearchFragment.goodsListAdapter.sortByPriceDesc();
                            break;
                    }
                    getInstance().dismiss();
                }
                @Override
                public void onFailure(Call<List<Part>> call, Throwable t) {}
            });
        }
        else Toast.makeText(getContext(),R.string.connection_error,Toast.LENGTH_SHORT).show();
    }

    private FilterBottomSheet getInstance(){
        return this;
    }

    public void setFilterType(int filterType) {this.filterType = filterType;}

    private void setPrice(Part filter){
        filter.setPriceFrom(sliderPrice.getValues().get(0)*1000/SearchFragment.EXCHANGE_RATE);
        filter.setPriceTo(sliderPrice.getValues().get(1)*1000/SearchFragment.EXCHANGE_RATE);
    }
}
