package com.example.dream_unit.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dream_unit.R;
import com.example.dream_unit.entity.Order;
import com.example.dream_unit.fragment.navigation.SearchFragment;

import java.util.List;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrderItemHolder> {
    private List<Order> orders;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Context context;
    private View orderDetailsView;
    private TextView tvGoodsName, tvGoodsCategory, tvOrderDate, tvOrderStatus, tvAmount, tvOrderSum;
    private String category;
    @Override
    public OrderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        orderDetailsView=LayoutInflater.from(context).inflate(R.layout.order_details_layout,null);
        tvGoodsName=orderDetailsView.findViewById(R.id.tvGoodsName);
        tvGoodsCategory=orderDetailsView.findViewById(R.id.tvGoodsCategory);
        tvOrderDate=orderDetailsView.findViewById(R.id.tvOrderDate);
        tvOrderStatus=orderDetailsView.findViewById(R.id.tvOrderStatus);
        tvAmount=orderDetailsView.findViewById(R.id.tvOrderAmount);
        tvOrderSum=orderDetailsView.findViewById(R.id.tvOrderSum);
        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderItemHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvOrder.setText(orders.get(position).getGoods());
        builder=new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle(R.string.order_details);
        builder.setView(orderDetailsView);
        builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGoodsName.setText(orders.get(position).getGoods());
                switch (orders.get(position).getGoodsType()){
                    case SearchFragment.CPU:
                        category=context.getString(R.string.cpu);
                        break;
                    case SearchFragment.VIDEO_CARD:
                        category=context.getString(R.string.video_card);
                        break;
                    case SearchFragment.MOBO:
                        category=context.getString(R.string.mobo);
                        break;
                    case SearchFragment.RAM:
                        category=context.getString(R.string.ram);
                        break;
                    case SearchFragment.PSU:
                        category=context.getString(R.string.psu);
                        break;
                    case SearchFragment.ASSEMBLY:
                        category=context.getString(R.string.assembly);
                        break;
                }
                tvGoodsCategory.setText(category);
                tvOrderDate.setText(orders.get(position).getOrderDate());
                tvOrderStatus.setText(orders.get(position).getStatus());
                tvAmount.setText(String.valueOf(orders.get(position).getAmount()));
                tvOrderSum.setText(String.valueOf(orders.get(position).getSum()));
                if(dialog==null)dialog=builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class OrderItemHolder extends RecyclerView.ViewHolder{
        private TextView tvOrder;
        public OrderItemHolder(View itemView) {
            super(itemView);
            tvOrder=itemView.findViewById(R.id.tvOrder);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }
}
