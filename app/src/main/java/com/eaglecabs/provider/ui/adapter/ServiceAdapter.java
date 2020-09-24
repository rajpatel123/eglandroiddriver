package com.eaglecabs.provider.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.data.network.model.RateCardService;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    private List<RateCardService> list;
    private Context context;
    private int lastCheckedPos = 0;

    public ServiceAdapter(Context context, List<RateCardService> list) {
        this.context = context;
        this.list = list;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName, perKm, perMinute,basekm,basefare;

        MyViewHolder(View view) {
            super(view);
            serviceName = view.findViewById(R.id.service_name);
            perKm = view.findViewById(R.id.per_km);
            perMinute = view.findViewById(R.id.per_minute);
            basekm = view.findViewById(R.id.base_km);
            basefare = view.findViewById(R.id.base_fare);

        }


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_service_rate_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RateCardService item = list.get(position);

        holder.serviceName.setText(item.getName());
        holder.perMinute.setText("\u2022 Extra time will be charged to you at \u20B9" + item.getRentalMinutePrice() + " per minute.");
//        holder.perKm("Extra distance will be charged to you at \u20B9" + item.getRentalKmPrice() + " per km.");
//        holder.basekm("Extra distance will be charged to you at \u20B9" + item.setOutstationBaseKm() + " per km.");


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public RateCardService getSelectedService() {
        if (list.size() > 0) {
            return list.get(lastCheckedPos);
        } else {
            return null;
        }
    }

    public RateCardService getItem(int pos) {
        return list.get(pos);
    }


}
