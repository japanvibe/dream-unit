package com.example.dream_unit.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService instance;
    private Retrofit retrofit;
    private NetworkService(){
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiData.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (instance == null) {
            instance = new NetworkService();
        }
        return instance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
