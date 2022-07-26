package com.example.dream_unit.fragment.navigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dream_unit.AuthenticationActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.adapter.GoodsListAdapter;
import com.example.dream_unit.entity.Part;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {
    private RecyclerView rvFavorites;
    private GoodsListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout layoutFavorites;
    private ChipGroup cgFavorites;
    private int userId;
    private Call<List<Part>> call;
    private Integer chipId=null;
    public FavoriteFragment() {
        adapter=new GoodsListAdapter(GoodsListAdapter.TYPE_FAVORITE);
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(chipId==null)chipId=R.id.cpuChip;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite, container, false);
        userId=InitialActivity.userId;
        layoutFavorites=view.findViewById(R.id.layoutFavorites);
        layoutFavorites.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendCall();
                layoutFavorites.setRefreshing(false);
            }
        });
        cgFavorites=view.findViewById(R.id.cgFavorites);
        for (int i = 0; i < cgFavorites.getChildCount(); i++) {
            Chip chip=(Chip) cgFavorites.getChildAt(i);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chipId=chip.getId();
                    sendCall();
                }
            });
        }
        layoutManager=new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rvFavorites=view.findViewById(R.id.rvFavorites);
        rvFavorites.setLayoutManager(layoutManager);
        rvFavorites.setAdapter(adapter);
        cgFavorites.check(chipId);
        sendCall();
        return view;
    }
    @SuppressLint("NonConstantResourceId")
    private void sendCall(){
        if(InitialActivity.checkConnection()){
            switch (cgFavorites.getCheckedChipId()){
                case R.id.cpuChip:
                    adapter.setPartType(SearchFragment.CPU);
                    call=SearchFragment.api.getFavoriteCpus(userId);
                    break;
                case R.id.videoCardChip:
                    adapter.setPartType(SearchFragment.VIDEO_CARD);
                    call=SearchFragment.api.getFavoriteVideoCards(userId);
                    break;
                case R.id.moboChip:
                    adapter.setPartType(SearchFragment.MOBO);
                    call=SearchFragment.api.getFavoriteMobos(userId);
                    break;
                case R.id.ramChip:
                    adapter.setPartType(SearchFragment.RAM);
                    call=SearchFragment.api.getFavoriteRams(userId);
                    break;
                case R.id.psuChip:
                    adapter.setPartType(SearchFragment.PSU);
                    call=SearchFragment.api.getFavoritePsus(userId);
                    break;
            }
            if(call.isExecuted()) call=call.clone();
            call.enqueue(new Callback<List<Part>>() {
                @Override
                public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
                    adapter.setItems(response.body());
                    layoutManager.scrollToPosition(0);
                }
                @Override
                public void onFailure(Call<List<Part>> call, Throwable t) {}
            });
        }
        else Toast.makeText(getContext(),R.string.connection_error,Toast.LENGTH_SHORT).show();
    }
}
