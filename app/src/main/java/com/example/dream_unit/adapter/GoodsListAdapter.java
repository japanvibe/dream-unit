package com.example.dream_unit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dream_unit.DetailsActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.api.ApiData;
import com.example.dream_unit.entity.Order;
import com.example.dream_unit.entity.Part;
import com.example.dream_unit.entity.UserFavoritePart;
import com.example.dream_unit.fragment.dialog.OrderDialog;
import com.example.dream_unit.fragment.navigation.SearchFragment;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Response;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.GoodsItemHolder> {
    private List<Part> parts;
    private Context context;
    public static String PART_ID_TAG="partId";
    public static String PART_TYPE_TAG="partType";
    private final String ORDER_DIALOG_TAG="order_dialog";
    private Call<Integer> callAddToFavorite, callAddToAssembly;
    private UserFavoritePart userFavoritePart;
    public static final int TYPE_SEARCH=1;
    public static final int TYPE_FAVORITE=2;
    private final int SUCCESS=1;
    private int adapterType;
    private int partType;
    private Order order;
    private OrderDialog orderDialog;

    public GoodsListAdapter(int adapterType) {
        this.adapterType=adapterType;
        parts=new ArrayList<>();
        userFavoritePart=new UserFavoritePart();
        order=new Order();
    }

    @Override
    public GoodsItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.goods_item_layout, parent, false);
        userFavoritePart.setUserId(InitialActivity.userId);
        return new GoodsItemHolder(view);
    }

    @Override
    public void onBindViewHolder(GoodsItemHolder holder, @SuppressLint("RecyclerView") int position) {
        setFadeAnimation(holder.itemView);
        if(!parts.isEmpty()){
            PopupMenu popupMenu=new PopupMenu(context,holder.btnMenu);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.order:
                            orderDialog=new OrderDialog(context, parts.get(position), partType);
                            FragmentActivity fragmentActivity=(FragmentActivity) context;
                            orderDialog.show(fragmentActivity.getSupportFragmentManager(),ORDER_DIALOG_TAG);
                            return true;
                        case R.id.addToAssembly:
                            callAddToAssembly=SearchFragment.api.addToAssembly(InitialActivity.userId, partType, parts.get(position).getId());
                            sendCallAssembly(callAddToAssembly);
                            return true;
                    }
                    return false;
                }
            });
            holder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMenu.show();
                }
            });
            Picasso.get().load(ApiData.getImagesUrl()+parts.get(position).getName()+".jpg").into(holder.ivImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.layoutLoading.setVisibility(View.GONE);
                }
                @Override
                public void onError(Exception e) { }
            });
            holder.tvTitle.setText(parts.get(position).getBrand()+" "+parts.get(position).getName());
            int price=Math.round(parts.get(position).getPrice()* SearchFragment.EXCHANGE_RATE);
            holder.tvPrice.setText(price+" "+context.getString(R.string.currency));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DetailsActivity.class);
                    intent.putExtra(PART_ID_TAG,parts.get(position).getId());
                    intent.putExtra(PART_TYPE_TAG,partType);
                    context.startActivity(intent);
                }
            });
            holder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userFavoritePart.setPartId(parts.get(position).getId());
                    if(adapterType==TYPE_SEARCH){
                        switch (SearchFragment.spinnerCategory.getSelectedItemPosition()){
                            case SearchFragment.CPU:
                                callAddToFavorite=SearchFragment.api.addFavoriteCpu(userFavoritePart);
                                break;
                            case SearchFragment.VIDEO_CARD:
                                callAddToFavorite=SearchFragment.api.addFavoriteVideoCard(userFavoritePart);
                                break;
                            case SearchFragment.MOBO:
                                callAddToFavorite=SearchFragment.api.addFavoriteMobo(userFavoritePart);
                                break;
                            case SearchFragment.RAM:
                                callAddToFavorite=SearchFragment.api.addFavoriteRam(userFavoritePart);
                                break;
                            case SearchFragment.PSU:
                                callAddToFavorite=SearchFragment.api.addFavoritePsu(userFavoritePart);
                                break;
                        }
                    }
                    else{
                        switch (partType){
                            case SearchFragment.CPU:
                                callAddToFavorite=SearchFragment.api.removeFavoriteCpu(userFavoritePart);
                                break;
                            case SearchFragment.VIDEO_CARD:
                                callAddToFavorite=SearchFragment.api.removeFavoriteVideoCard(userFavoritePart);
                                break;
                            case SearchFragment.MOBO:
                                callAddToFavorite=SearchFragment.api.removeFavoriteMobo(userFavoritePart);
                                break;
                            case SearchFragment.RAM:
                                callAddToFavorite=SearchFragment.api.removeFavoriteRam(userFavoritePart);
                                break;
                            case SearchFragment.PSU:
                                callAddToFavorite=SearchFragment.api.removeFavoritePsu(userFavoritePart);
                                break;
                        }
                    }
                    sendCallFavorite(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
         return parts.size();
    }

    class GoodsItemHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle;
        private ImageView ivImage;
        private LinearLayout layoutLoading;
        private TextView tvPrice;
        private MaterialButton btnAdd;
        private View btnMenu;
        public GoodsItemHolder(View view){
            super(view);
            tvTitle=view.findViewById(R.id.tvTitle);
            ivImage=view.findViewById(R.id.ivImage);
            layoutLoading=view.findViewById(R.id.layoutLoading);
            tvPrice=view.findViewById(R.id.tvPrice);
            btnAdd=view.findViewById(R.id.btnAdd);
            btnMenu=view.findViewById(R.id.btnMenu);
            if(adapterType==TYPE_FAVORITE){
                btnAdd.setText(R.string.remove);
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<Part> _parts){
        parts=_parts;
        if(adapterType==TYPE_FAVORITE) Collections.reverse(parts);
        notifyDataSetChanged();
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation animation=new AlphaAnimation(0.0f,1.0f);
        animation.setDuration(500);
        view.startAnimation(animation);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortByPriceAsc(){
        parts.sort(new Comparator<Part>() {
            @Override
            public int compare(Part o1, Part o2) {
                return Math.round(o1.getPrice())-Math.round(o2.getPrice());
            }
        });
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void sortByPriceDesc(){
        parts.sort(new Comparator<Part>() {
            @Override
            public int compare(Part o1, Part o2) {
                return Math.round(o2.getPrice())-Math.round(o1.getPrice());
            }
        });
        notifyDataSetChanged();
    }

    private void sendCallFavorite(int position){
        if(InitialActivity.checkConnection()){
            callAddToFavorite.enqueue(new retrofit2.Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> callAddToFavorite, Response<Integer> response) {
                    if(adapterType==TYPE_SEARCH) {
                        if(response.body()==SUCCESS) Toast.makeText(context, R.string.part_added, Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context, R.string.already_added, Toast.LENGTH_SHORT).show();
                    }
                    if(adapterType==TYPE_FAVORITE) {
                        Toast.makeText(context, R.string.part_removed, Toast.LENGTH_SHORT).show();
                        parts.remove(position);
                        notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Integer> callAddToFavorite, Throwable t) {
                }
            });
        }
        else  Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }


    public void sendCallAssembly(Call<Integer> call){
        if(InitialActivity.checkConnection()){
            call.enqueue(new retrofit2.Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> callAddToFavorite, Response<Integer> response) {
                    Toast.makeText(context, R.string.update_assembly, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Integer> callAddToFavorite, Throwable t) {}
            });
        }
        else Toast.makeText(context, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    public void setPartType(int partType) {
        this.partType = partType;
    }

    public List<Part> getItems(){
        return parts;
    }
}
