package com.eaglecabs.provider.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eaglecabs.provider.MvpApplication;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.data.network.model.Ride;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.eaglecabs.provider.common.Utilities.getTime;
import static com.eaglecabs.provider.common.Utilities.getDate;


/**
 * Created by suthakar@appoets.com on 28-06-2018.
 */
public class EarningsPastTripAdapter extends RecyclerView.Adapter<EarningsPastTripAdapter.MyViewHolder> {

    private List<Ride> list;
    private Context context;
    private NumberFormat numberFormat;

    public EarningsPastTripAdapter(List<Ride> list, Context con) {
        this.list = list;
        this.context = con;
        numberFormat = MvpApplication.getNumberFormat();
    }

    public void setList(List<Ride> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_earnings_past, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride ride = list.get(position);

        holder.lblDistance.setText(ride.getDistance() + " Km");
        if (ride.getPayment() != null)
            holder.lblAmount.setText(numberFormat.format(ride.getPayment().getProviderPay()));
        else
            holder.lblAmount.setText("-");
        try {
            holder.lblDate.setText(getDate(ride.getAssignedAt()) + " " + getTime(ride.getAssignedAt()));

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException n) {
            n.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView lblTime, lblDate, lblDistance, lblAmount;

        private MyViewHolder(View view) {
            super(view);

            lblDate = view.findViewById(R.id.lblDate);
            lblDistance = view.findViewById(R.id.lblDistance);
            lblAmount = view.findViewById(R.id.lblAmount);


        }
    }
}