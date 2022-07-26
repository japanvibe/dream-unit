package com.example.dream_unit.fragment.navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.dream_unit.AuthenticationActivity;
import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.adapter.OrdersListAdapter;
import com.example.dream_unit.entity.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private OrdersListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayout layoutLoading;
    private RecyclerView rvOrders;
    public OrderFragment() {}

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        adapter=new OrdersListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_order, container, false);
        layoutLoading=view.findViewById(R.id.layoutLoading);
        rvOrders=view.findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(layoutManager);
        Call<List<Order>> call=SearchFragment.api.getOrders(InitialActivity.userId);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                adapter.setOrders(response.body());
                rvOrders.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {}
        });
        return view;
    }

}