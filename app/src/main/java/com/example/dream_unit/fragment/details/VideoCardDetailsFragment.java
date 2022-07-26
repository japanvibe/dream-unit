package com.example.dream_unit.fragment.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.VideoCard;
import com.example.dream_unit.fragment.navigation.SearchFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoCardDetailsFragment extends Fragment {
    private VideoCard videoCard;
    private TextView tvCoreClock, tvGpu, tvMemory;
    public VideoCardDetailsFragment() {}


    public static VideoCardDetailsFragment newInstance(String param1, String param2) {
        return new VideoCardDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_video_card_details, container, false);
        tvCoreClock=view.findViewById(R.id.tvCoreClock);
        tvGpu=view.findViewById(R.id.tvGpu);
        tvMemory=view.findViewById(R.id.tvVideoCardMemory);
        tvCoreClock.setText(String.valueOf(videoCard.getCoreClock()));
        tvGpu.setText(videoCard.getGpu());
        Call<Integer> call=SearchFragment.api.getMemoryCapacity(videoCard.getGpu());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                tvMemory.setText(String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {}
        });
        return view;
    }

    public void setVideoCard(VideoCard videoCard) {
        this.videoCard = videoCard;
    }
}