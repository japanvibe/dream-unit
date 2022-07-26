package com.example.dream_unit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import java.util.Objects;

public class InitialActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Intent intent;
    public static int userId;
    private static ConnectivityManager connectivityManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        Objects.requireNonNull(getSupportActionBar()).hide();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences=getSharedPreferences(AuthenticationActivity.USER_DATA, Context.MODE_PRIVATE);
                userId=sharedPreferences.getInt(AuthenticationActivity.USER_ID,0);
                connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                String userStatus=sharedPreferences.getString(AuthenticationActivity.STATUS,"");
                if(userStatus.equals(AuthenticationActivity.LOGGED)) intent = new Intent(InitialActivity.this, MainActivity.class);
                else intent = new Intent(InitialActivity.this, AuthenticationActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }

    public static boolean checkConnection(){
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null) return networkInfo.isConnected();
        return false;
    }
}