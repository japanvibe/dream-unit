package com.example.dream_unit.fragment.navigation;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dream_unit.InitialActivity;
import com.example.dream_unit.R;
import com.example.dream_unit.adapter.AssemblyListAdapter;
import com.example.dream_unit.entity.Order;
import com.example.dream_unit.entity.Part;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssemblyFragment extends Fragment {
    private Call<List<Part>> call;
    private RecyclerView rvAssembly;
    public static TextView tvAssemblySum;
    private Button btnOrderAssembly;
    private AssemblyListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private final int RAM=3;

    public AssemblyFragment() {
        adapter=new AssemblyListAdapter();
    }

    public static AssemblyFragment newInstance() {
        return new AssemblyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new AssemblyListAdapter();
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_assembly, container, false);
       rvAssembly=view.findViewById(R.id.rvAssembly);
       rvAssembly.setLayoutManager(layoutManager);
       tvAssemblySum=view.findViewById(R.id.tvAssemblySum);
       call=SearchFragment.api.getAssembly(InitialActivity.userId);
       call.enqueue(new Callback<List<Part>>() {
           @Override
           public void onResponse(Call<List<Part>> call, Response<List<Part>> response) {
               adapter.setAssembly(response.body());
               rvAssembly.setAdapter(adapter);
               adapter.refreshSum();
           }
           @Override
           public void onFailure(Call<List<Part>> call, Throwable t) {
           }
       });
       btnOrderAssembly=view.findViewById(R.id.btnOrderAssembly);
       btnOrderAssembly.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               List<String> goods=new ArrayList<>();
               Boolean assemblyIsComplete=true;
               for (Part part : adapter.getAssembly()) {
                   if(part==null) {
                       Toast.makeText(getContext(), getString(R.string.assembly_not_complete), Toast.LENGTH_SHORT).show();
                       assemblyIsComplete=false;
                       goods.clear();
                       break;
                   }
                   goods.add(part.getBrand()+" "+part.getName());
               }
               if(assemblyIsComplete){
                   String ram=goods.get(RAM);
                   ram+=" "+adapter.getRamAmount()+getString(R.string.amt);
                   goods.set(RAM,ram);
                   String goodsStr=TextUtils.join(", ",goods);
                   Order order=new Order();
                   order.setUserId(InitialActivity.userId);
                   order.setGoods(goodsStr);
                   order.setGoodsType(SearchFragment.ASSEMBLY);
                   order.setAmount(1);
                   order.setSum(adapter.getSum());
                   Call<Integer> callOrder=SearchFragment.api.orderGood(order);
                   sendCallOrder(callOrder);
               }
           }
       });
       return  view;
    }
    private void sendCallOrder(Call<Integer> call){
        if(InitialActivity.checkConnection()){
            call.enqueue(new retrofit2.Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> callAddToFavorite, Response<Integer> response) {
                    Toast.makeText(getContext(), getString(R.string.success_assembly_order), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<Integer> callAddToFavorite, Throwable t) {}
            });
        }
        else Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }
}