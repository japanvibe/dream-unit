package com.example.dream_unit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dream_unit.DetailsActivity;
import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.api.ApiData;
import com.example.dream_unit.entity.Part;
import com.example.dream_unit.fragment.navigation.AssemblyFragment;
import com.example.dream_unit.fragment.navigation.SearchFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AssemblyListAdapter extends RecyclerView.Adapter<AssemblyListAdapter.AssemblyItemHolder> {
    private static final String PART_ID_TAG = "partId";
    private static final String PART_TYPE_TAG = "partType";
    private List<Part> assembly;
    private Context context;
    private Call<Integer> call;
    private int ramAmount=1;
    private int sum;
    @NonNull
    @Override
    public AssemblyItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.assembly_item_layout, parent, false);
        return new AssemblyItemHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AssemblyItemHolder holder, @SuppressLint("RecyclerView") int position) {
        int type=position+1;
        switch (type){
            case SearchFragment.CPU:
                holder.tvItemType.setText(R.string.cpu);
                break;
            case SearchFragment.VIDEO_CARD:
                holder.tvItemType.setText(R.string.video_card);
                break;
            case SearchFragment.MOBO:
                holder.tvItemType.setText(R.string.mobo);
                break;
            case SearchFragment.RAM:
                holder.tvItemType.setText(R.string.ram);
                holder.tvRamAmount.setVisibility(View.VISIBLE);
                holder.tvRamAmountValue.setVisibility(View.VISIBLE);
                holder.tvRamAmountValue.setText(String.valueOf(ramAmount));
                holder.btnMinus.setVisibility(View.VISIBLE);
                holder.btnPlus.setVisibility(View.VISIBLE);
                holder.btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ramAmount>1) {
                            ramAmount--;
                            sum-=Math.round(assembly.get(position).getPrice()*SearchFragment.EXCHANGE_RATE);
                            AssemblyFragment.tvAssemblySum.setText(String.valueOf(sum));
                            holder.tvRamAmountValue.setText(String.valueOf(ramAmount));
                        }
                    }
                });
                holder.btnPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ramAmount++;
                        sum+=Math.round(assembly.get(position).getPrice()*SearchFragment.EXCHANGE_RATE);
                        AssemblyFragment.tvAssemblySum.setText(String.valueOf(sum));
                        holder.tvRamAmountValue.setText(String.valueOf(ramAmount));
                    }
                });
                break;
            case SearchFragment.PSU:
                holder.tvItemType.setText(R.string.psu);
                break;
        }
        if(assembly.get(position)!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DetailsActivity.class);
                    intent.putExtra(PART_ID_TAG,assembly.get(position).getId());
                    intent.putExtra(PART_TYPE_TAG,position+1);
                    context.startActivity(intent);
                }
            });
            Picasso.get().load(ApiData.getImagesUrl()+assembly.get(position).getName()+".jpg").into(holder.ivItemImage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.layoutImageLoading.setVisibility(View.GONE);
                }
                @Override
                public void onError(Exception e) {}
            });
            holder.tvItemTitle.setText(assembly.get(position).getBrand()+" "+assembly.get(position).getName());
            holder.tvItemPrice.setText(String.valueOf(Math.round(assembly.get(position).getPrice() * SearchFragment.EXCHANGE_RATE)));
        }
        else {
            setEmptyPart(holder);
        }
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call=SearchFragment.api.removeFromAssembly(InitialActivity.userId, position+1, assembly.get(position).getId());
                call.enqueue(new retrofit2.Callback<Integer>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        assembly.set(position, null);
                        notifyDataSetChanged();
                        refreshSum();
                    }
                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {}
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return assembly.size();
    }

    class AssemblyItemHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutImageLoading;
        private ImageView ivItemImage;
        private TextView tvItemType, tvItemTitle, tvItemPrice, tvCurrency, tvRamAmount, tvRamAmountValue;
        private ImageButton btnRemove, btnPlus, btnMinus;
        public AssemblyItemHolder(@NonNull View itemView) {
            super(itemView);
            layoutImageLoading=itemView.findViewById(R.id.layoutImageLoading);
            ivItemImage=itemView.findViewById(R.id.ivItemImage);
            tvItemType=itemView.findViewById(R.id.tvItemType);
            tvItemTitle=itemView.findViewById(R.id.tvItemTitle);
            tvItemPrice=itemView.findViewById(R.id.tvItemPrice);
            tvCurrency=itemView.findViewById(R.id.tvCurrency);
            btnRemove=itemView.findViewById(R.id.btnItemRemove);
            tvRamAmount=itemView.findViewById(R.id.tvRamAmount);
            tvRamAmountValue=itemView.findViewById(R.id.tvRamAmountValue);
            btnPlus=itemView.findViewById(R.id.btnPlus);
            btnMinus=itemView.findViewById(R.id.btnMinus);
            tvRamAmount.setVisibility(View.GONE);
            tvRamAmountValue.setVisibility(View.GONE);
            btnMinus.setVisibility(View.GONE);
            btnPlus.setVisibility(View.GONE);
        }
    }

    public List<Part> getAssembly() {
        return assembly;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAssembly(List<Part> assembly) {
        this.assembly = assembly;
        notifyDataSetChanged();
        refreshSum();
    }

    private void setEmptyPart(AssemblyItemHolder holder){
        holder.layoutImageLoading.setVisibility(View.GONE);
        holder.ivItemImage.setImageResource(R.drawable.ic_channel_foreground);
        holder.tvItemTitle.setText(R.string.empty);
        holder.tvItemPrice.setText("");
        holder.tvItemPrice.setVisibility(View.GONE);
        holder.tvCurrency.setText("");
        holder.tvCurrency.setVisibility(View.GONE);
        holder.btnRemove.setVisibility(View.GONE);
        holder.tvRamAmount.setVisibility(View.GONE);
        holder.tvRamAmountValue.setVisibility(View.GONE);
        holder.btnMinus.setVisibility(View.GONE);
        holder.btnPlus.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    public void refreshSum() {
        sum=0;
        for (Part part : assembly) {
            if(part!=null)sum+=Math.round(part.getPrice()*SearchFragment.EXCHANGE_RATE);
        }
        AssemblyFragment.tvAssemblySum.setText(String.valueOf(sum));
    }

    public int getRamAmount() {
        return ramAmount;
    }

    public int getSum() {
        return sum;
    }
}
