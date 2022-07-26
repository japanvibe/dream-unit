package com.example.dream_unit.fragment.navigation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.dream_unit.R;
import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.adapter.GoodsListAdapter;
import com.example.dream_unit.api.Api;
import com.example.dream_unit.api.NetworkService;
import com.example.dream_unit.entity.Part;
import com.example.dream_unit.fragment.bottom_sheet.FilterBottomSheet;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    public static Api api;
    private SwipeRefreshLayout layoutList;
    private RecyclerView rvItemList;
    public static GoodsListAdapter goodsListAdapter;
    public static Spinner spinnerCategory;
    public static ChipGroup chipGroup;
    private Chip chipPriceAsc, chipPriceDesc;
    private ImageButton btnFilters;
    private String[] categories;
    private ArrayAdapter<String> categoryAdapter;
    public static RecyclerView.LayoutManager layoutManager;
    private final int NO_CHOICE=0;
    public static final int CPU=1;
    public static final int VIDEO_CARD=2;
    public static final int MOBO=3;
    public static final int RAM=4;
    public static final int PSU=5;
    public static final int ASSEMBLY=6;
    private final String FILTERS_TAG="FilterBottomSheet";
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Call<List<Part>> call;
    private FilterBottomSheet filters;
    private int selectedPosition;
    public static final float EXCHANGE_RATE=446.93f;

    public SearchFragment() {
        api=NetworkService.getInstance().getApi();
        goodsListAdapter=new GoodsListAdapter(GoodsListAdapter.TYPE_SEARCH);
        selectedPosition=NO_CHOICE;
        filters=new FilterBottomSheet();
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        layoutList=view.findViewById(R.id.layoutList);
        layoutList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(spinnerCategory.getSelectedItemPosition()!=NO_CHOICE) {
                    sendCall(call.clone());
                }
                layoutList.setRefreshing(false);
            }
        });
        rvItemList=view.findViewById(R.id.rvItemList);
        spinnerCategory=view.findViewById(R.id.spinnerCategory);
        btnFilters=view.findViewById(R.id.btnFilters);
        chipGroup=view.findViewById(R.id.cgSorting);
        chipGroup.setSingleSelection(true);
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip=(Chip) chipGroup.getChildAt(i);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutManager.scrollToPosition(0);
                    switch (chip.getId()){
                        case R.id.chipPriceAsc:
                            goodsListAdapter.sortByPriceAsc();
                            break;
                        case R.id.chipPriceDesc:
                            goodsListAdapter.sortByPriceDesc();
                            break;
                    }
                }
            });
        }
        btnFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinnerCategory.getSelectedItemPosition()!=NO_CHOICE) {
                    switch (spinnerCategory.getSelectedItemPosition()){
                        case CPU:
                            filters.setFilterType(CPU);
                            break;
                        case VIDEO_CARD:
                            filters.setFilterType(VIDEO_CARD);
                            break;
                        case MOBO:
                            filters.setFilterType(MOBO);
                            break;
                        case RAM:
                            filters.setFilterType(RAM);
                            break;
                        case PSU:
                            filters.setFilterType(PSU);
                            break;
                    }
                    filters.show(getChildFragmentManager(), FILTERS_TAG);
                }
            }
        });
        categories=getResources().getStringArray(R.array.categories);
        builder=new AlertDialog.Builder(getActivity());
        categoryAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,categories);
        rvItemList.setLayoutManager(layoutManager);
        rvItemList.setAdapter(goodsListAdapter);
        spinnerCategory.setAdapter(categoryAdapter);
        builder.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {sendCall(call);}
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        }).setMessage(R.string.connection_error);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position!=NO_CHOICE&&position!=selectedPosition){
                            switch (position) {
                                case CPU:
                                    call = api.getCpus();
                                    break;
                                case VIDEO_CARD:
                                    call = api.getVideoCards();
                                    break;
                                case MOBO:
                                    call = api.getMobos();
                                    break;
                                case RAM:
                                    call = api.getRams();
                                    break;
                                case PSU:
                                    call = api.getPsus();
                                    break;
                                default:
                                    break;
                            }
                            selectedPosition=position;
                            goodsListAdapter.setPartType(position);
                            sendCall(call);
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return view;
    }
    private void sendCall(Call<List<Part>> call){
        layoutManager.scrollToPosition(0);
        if(InitialActivity.checkConnection()) {
            call.enqueue(new Callback<List<Part>>() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
                    goodsListAdapter.setItems(response.body());
                    switch (chipGroup.getCheckedChipId()){
                        case R.id.chipPriceAsc:
                            goodsListAdapter.sortByPriceAsc();
                            break;
                        case R.id.chipPriceDesc:
                            goodsListAdapter.sortByPriceDesc();
                            break;
                    }
                }
                @Override
                public void onFailure(Call<List<Part>> call, Throwable t) {
                }
            });
        }
        else {
            dialog=builder.create();
            dialog.show();
        }
    }
}