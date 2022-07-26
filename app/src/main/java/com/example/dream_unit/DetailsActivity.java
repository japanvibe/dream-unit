package com.example.dream_unit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dream_unit.adapter.GoodsListAdapter;
import com.example.dream_unit.api.ApiData;
import com.example.dream_unit.entity.Cpu;
import com.example.dream_unit.entity.Mobo;
import com.example.dream_unit.entity.Part;
import com.example.dream_unit.entity.Psu;
import com.example.dream_unit.entity.Ram;
import com.example.dream_unit.entity.VideoCard;
import com.example.dream_unit.fragment.details.CpuDetailsFragment;
import com.example.dream_unit.fragment.details.MoboDetailsFragment;
import com.example.dream_unit.fragment.details.PsuDetailsFragment;
import com.example.dream_unit.fragment.details.RamDetailsFragment;
import com.example.dream_unit.fragment.details.VideoCardDetailsFragment;
import com.example.dream_unit.fragment.navigation.SearchFragment;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private LinearLayout layoutDetailsImageLoading;
    private ImageView ivPcPartImage;
    private TextView tvDetailsBrand, tvDetailsName, tvDetailsPrice;
    private Part pcPart;
    private int partType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle(R.string.details);
        layoutDetailsImageLoading=findViewById(R.id.layoutDetailsImageLoading);
        ivPcPartImage=findViewById(R.id.ivPcPartImage);
        tvDetailsBrand=findViewById(R.id.tvDetailsBrand);
        tvDetailsName=findViewById(R.id.tvDetailsName);
        tvDetailsPrice=findViewById(R.id.tvDetailsPrice);
        int partId=getIntent().getIntExtra(GoodsListAdapter.PART_ID_TAG,0);
        partType=getIntent().getIntExtra(GoodsListAdapter.PART_TYPE_TAG,-1);
        sendCall(partId);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendCall(int id) {
        Call call=null;
        switch (partType){
            case SearchFragment.CPU:
                call=SearchFragment.api.getCpu(id);
                break;
            case SearchFragment.VIDEO_CARD:
                call=SearchFragment.api.getVideoCard(id);
                break;
            case SearchFragment.MOBO:
                call=SearchFragment.api.getMobo(id);
                break;
            case SearchFragment.RAM:
                call=SearchFragment.api.getRam(id);
                break;
            case SearchFragment.PSU:
                call=SearchFragment.api.getPsu(id);
                break;
        }
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                pcPart=(Part)response.body();
                Picasso.get().load(ApiData.getImagesUrl()+pcPart.getName()+".jpg").into(ivPcPartImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        layoutDetailsImageLoading.setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {}
                });
                tvDetailsBrand.setText(pcPart.getBrand().toString());
                tvDetailsName.setText(pcPart.getName());
                tvDetailsPrice.setText(String.valueOf(Math.round(pcPart.getPrice()*SearchFragment.EXCHANGE_RATE)));
                switch (partType){
                    case SearchFragment.CPU:
                        Cpu cpu=(Cpu) response.body();
                        CpuDetailsFragment cpuDetailsFragment=new CpuDetailsFragment();
                        cpuDetailsFragment.setCpu(cpu);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutDetails, cpuDetailsFragment).commit();
                        break;
                    case SearchFragment.VIDEO_CARD:
                        VideoCard videoCard=(VideoCard) response.body();
                        VideoCardDetailsFragment videoCardDetailsFragment=new VideoCardDetailsFragment();
                        videoCardDetailsFragment.setVideoCard(videoCard);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutDetails, videoCardDetailsFragment).commit();
                        break;
                    case SearchFragment.MOBO:
                        Mobo mobo=(Mobo) response.body();
                        MoboDetailsFragment moboDetailsFragment=new MoboDetailsFragment();
                        moboDetailsFragment.setMobo(mobo);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutDetails, moboDetailsFragment).commit();
                        break;
                    case SearchFragment.RAM:
                        Ram ram=(Ram) response.body();
                        RamDetailsFragment ramDetailsFragment=new RamDetailsFragment();
                        ramDetailsFragment.setRam(ram);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutDetails, ramDetailsFragment).commit();
                        break;
                    case SearchFragment.PSU:
                        Psu psu=(Psu) response.body();
                        PsuDetailsFragment psuDetailsFragment=new PsuDetailsFragment();
                        psuDetailsFragment.setPsu(psu);
                        getSupportFragmentManager().beginTransaction().replace(R.id.layoutDetails, psuDetailsFragment).commit();
                        break;
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {}
        });
    }
}