package com.example.dream_unit.api;

import com.example.dream_unit.entity.Cpu;
import com.example.dream_unit.entity.Mobo;
import com.example.dream_unit.entity.Order;
import com.example.dream_unit.entity.Part;
import com.example.dream_unit.entity.Psu;
import com.example.dream_unit.entity.Ram;
import com.example.dream_unit.entity.RamType;
import com.example.dream_unit.entity.User;
import com.example.dream_unit.entity.UserFavoritePart;
import com.example.dream_unit.entity.VideoCard;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    //parts
    @GET("cpu")
    Call<List<Part>> getCpus();
    @GET("mobo")
    Call<List<Part>> getMobos();
    @GET("ram")
    Call<List<Part>> getRams();
    @GET("video_card")
    Call<List<Part>> getVideoCards();
    @GET("psu")
    //users
    Call<List<Part>> getPsus();
    @POST("users/create")
    Call<Integer> createUser(@Body User user);
    @POST("users/check")
    Call<Integer> checkUser(@Body User user);
    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);
    @PUT("users/update/{id}")
    Call<Integer> updateUser(@Path("id") int id, @Body User user);
    //filters
    @POST("cpu/filter")
    Call<List<Cpu>> filterCpu(@Body Cpu filter);
    @POST("video_card/filter")
    Call<List<VideoCard>> filterVideoCard(@Body VideoCard filter);
    @POST("mobo/filter")
    Call<List<Mobo>> filterMobo(@Body Mobo filter);
    @POST("ram/filter")
    Call<List<Ram>> filterRam(@Body Ram filter);
    @POST("psu/filter")
    Call<List<Psu>> filterPsu(@Body Psu filter);
    //getItem
    @GET("cpu/{id}")
    Call<Cpu> getCpu(@Path("id") int id);
    @GET("video_card/{id}")
    Call<VideoCard> getVideoCard(@Path("id") int id);
    @GET("gpu/{name}")
    Call<Integer> getMemoryCapacity(@Path("name") String name);
    @GET("ram/{id}")
    Call<Ram> getRam(@Path("id") int id);
    @GET("ram_type/{speed}")
    Call<RamType> getRamType(@Path("speed") int speed);
    @GET("mobo/{id}")
    Call<Mobo> getMobo(@Path("id") int id);
    @GET("psu/{id}")
    Call<Psu> getPsu(@Path("id") int id);
    //add to favorites
    @POST("add_favorite/cpu")
    Call<Integer> addFavoriteCpu(@Body UserFavoritePart userFavoritePart);
    @POST("add_favorite/video_card")
    Call<Integer> addFavoriteVideoCard(@Body UserFavoritePart userFavoritePart);
    @POST("add_favorite/mobo")
    Call<Integer> addFavoriteMobo(@Body UserFavoritePart userFavoritePart);
    @POST("add_favorite/ram")
    Call<Integer> addFavoriteRam(@Body UserFavoritePart userFavoritePart);
    @POST("add_favorite/psu")
    Call<Integer> addFavoritePsu(@Body UserFavoritePart userFavoritePart);
    //remove from favorites
    @POST("remove_favorite/cpu")
    Call<Integer> removeFavoriteCpu(@Body UserFavoritePart userFavoritePart);
    @POST("remove_favorite/video_card")
    Call<Integer> removeFavoriteVideoCard(@Body UserFavoritePart userFavoritePart);
    @POST("remove_favorite/mobo")
    Call<Integer> removeFavoriteMobo(@Body UserFavoritePart userFavoritePart);
    @POST("remove_favorite/ram")
    Call<Integer> removeFavoriteRam(@Body UserFavoritePart userFavoritePart);
    @POST("remove_favorite/psu")
    Call<Integer> removeFavoritePsu(@Body UserFavoritePart userFavoritePart);
    //get favorites
    @GET("user/{userId}/favorites/cpu")
    Call<List<Part>> getFavoriteCpus(@Path("userId") int userId);
    @GET("user/{userId}/favorites/video_card")
    Call<List<Part>> getFavoriteVideoCards(@Path("userId") int userId);
    @GET("user/{userId}/favorites/mobo")
    Call<List<Part>> getFavoriteMobos(@Path("userId") int userId);
    @GET("user/{userId}/favorites/ram")
    Call<List<Part>> getFavoriteRams(@Path("userId") int userId);
    @GET("user/{userId}/favorites/psu")
    Call<List<Part>> getFavoritePsus(@Path("userId") int userId);
    //orders
    @POST("orders/new_order")
    Call<Integer> orderGood(@Body Order order);
    @GET("orders/{userId}")
    Call<List<Order>> getOrders(@Path("userId") int userId);
    //assembly
    @GET("assembly/add/{userId}/{type}/{partId}")
    Call<Integer> addToAssembly(@Path("userId") int userId, @Path("type") int type, @Path("partId") int partId);
    @GET("assembly/remove/{userId}/{type}/{partId}")
    Call<Integer> removeFromAssembly(@Path("userId") int userId, @Path("type") int type, @Path("partId") int partId);
    @GET("assembly/{userId}")
    Call<List<Part>> getAssembly(@Path("userId") int userId);
}
