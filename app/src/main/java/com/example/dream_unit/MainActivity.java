package com.example.dream_unit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.dream_unit.fragment.navigation.AssemblyFragment;
import com.example.dream_unit.fragment.navigation.FavoriteFragment;
import com.example.dream_unit.fragment.navigation.OrderFragment;
import com.example.dream_unit.fragment.navigation.ProfileFragment;
import com.example.dream_unit.fragment.navigation.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private SearchFragment searchFragment;
    private FavoriteFragment favoriteFragment;
    private AssemblyFragment assemblyFragment;
    private OrderFragment orderFragment;
    private ProfileFragment profileFragment;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        searchFragment=SearchFragment.newInstance();
        favoriteFragment=FavoriteFragment.newInstance();
        assemblyFragment=AssemblyFragment.newInstance();
        orderFragment=OrderFragment.newInstance();
        profileFragment=ProfileFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer, searchFragment).commit();
        getSupportActionBar().setTitle(R.string.search);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        getSupportActionBar().setTitle(R.string.search);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer, searchFragment).commit();
                        return true;
                    case R.id.favorite:
                        getSupportActionBar().setTitle(R.string.favorite);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer, favoriteFragment).commit();
                        return true;
                    case R.id.assembly:
                        getSupportActionBar().setTitle(R.string.assembly);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer, assemblyFragment).commit();
                        return true;
                    case R.id.orders:
                        getSupportActionBar().setTitle(R.string.orders);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer, orderFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportActionBar().setTitle(R.string.profile);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutContainer,profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}