package com.eaglecabs.provider.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.data.network.model.RateCardService;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {
    private List<RateCardService> list;
     Context context;
     int lastCheckedPos = 0;

    public ServiceAdapter(Context context, List<RateCardService> list) {
        this.context = context;
        this.list = list;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName, OutstationPerKm, OutstationPerMinute
                ,RentalPerKm,RentalPerMinute;

        MyViewHolder(View view) {
            super(view);
            serviceName = view.findViewById(R.id.service_name);
            OutstationPerKm = view.findViewById(R.id.Outstation_per_km);
            OutstationPerMinute = view.findViewById(R.id.Outstation_per_minute);

            RentalPerKm = view.findViewById(R.id.Rental_per_km);
            RentalPerMinute = view.findViewById(R.id.Rental_per_minute);

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
        holder.OutstationPerMinute.setText("\u2022 One way fare  \u20B9" + item.getOutstationOnewayPrice() + " per km.");
        holder.OutstationPerKm.setText("\u2022 Round Trip fare \u20B9" + item.getRoundtripKm() + " per km.");

        holder.RentalPerMinute.setText("\u2022 Extra time will be charged at \u20B9" + item.getRentalMinutePrice() + " per minute.");
        holder.RentalPerKm.setText("\u2022 Extra km will be charged at \u20B9" + item.getRentalKmPrice() + " per km.");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

/*
    public RateCardService getSelectedService() {
        if (list.size() > 0) {
            return list.get(lastCheckedPos);
        } else {
            return null;
        }
    }

    public RateCardService getItem(int pos) {
        return list.get(pos);
    }*/


}
