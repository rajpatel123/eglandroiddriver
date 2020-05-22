package com.eaglecabs.provider.data.scheduledrides;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eaglecabs.provider.R;

import java.util.List;

/**
 * Created by suthakar@appoets.com on 28-06-2018.
 */
public class ScheduledTripsAdapter extends RecyclerView.Adapter<ScheduledTripsAdapter.MyViewHolder> {

    private List<ScheduledReidesResponse> list;
    private Context context;

    private ClickListener clickListener;

    public ScheduledTripsAdapter(List<ScheduledReidesResponse> list, Context con) {
        this.list = list;
        this.context = con;
    }

    public void setList(List<ScheduledReidesResponse> list) {
        this.list = list;
    }

    public void setClickListener(ScheduledTripsAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void acceptRide(ScheduledReidesResponse scheduledReidesResponse);

        void rejectRide(ScheduledReidesResponse shScheduledReidesResponse, int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_scheduled_trip, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ScheduledReidesResponse scheduledReidesResponse = list.get(position);

        holder.lblDate.setText("Departure Date: "+scheduledReidesResponse.getScheduleAt());
        holder.lblBookingid.setText("Booking Id: "+scheduledReidesResponse.getBookingId());
        holder.lblSeviceName.setText("Service Type :"+scheduledReidesResponse.getServiceType().getName());
        holder.lblBookingType.setText("Trip :"+scheduledReidesResponse.getDay());
        holder.lblFare.setText("Total Fare: Rs. " + scheduledReidesResponse.getTotalAmount());
        holder.lblPaymentMode.setText("Payment Mode: " + scheduledReidesResponse.getPaymentMode());
        holder.lblPaid.setText("Paid fare:  Rs. " + scheduledReidesResponse.getPaid());

//        if (scheduledReidesResponse.getPaid()>0){
//            holder.lblPaid.setText("Paid fare Rs. " + scheduledReidesResponse.getPaid());
//            holder.lblPaid.setVisibility(View.VISIBLE);
//        }else{
//            holder.lblPaid.setVisibility(View.GONE);
//        }


        // Glide.with(context).load(scheduledReidesResponse.get()).apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background).dontAnimate().error(R.drawable.ic_launcher_background)).into(holder.staticMap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView itemView;
        private TextView lblDate, lblBookingid, lblSeviceName, sAddress, dAddress, lblFare, lblPaid,lblBookingType,lblPaymentMode;
        private ImageView staticMap, avatar;
        private TextView btnReject, btnAccept;

        private MyViewHolder(View view) {
            super(view);

            itemView = view.findViewById(R.id.item_view);
            lblDate = view.findViewById(R.id.lblDate);
            lblBookingid = view.findViewById(R.id.lblBookingid);
            lblSeviceName = view.findViewById(R.id.lblSeviceName);
            lblFare = view.findViewById(R.id.lblFare);
            lblPaid = view.findViewById(R.id.lblPaid);
            lblPaymentMode = view.findViewById(R.id.lblPaymentMode);
            lblBookingType = view.findViewById(R.id.lblBookingType);
            staticMap = view.findViewById(R.id.static_map);
            avatar = view.findViewById(R.id.avatar);

            btnReject = view.findViewById(R.id.btnReject);
            btnAccept = view.findViewById(R.id.btnAccept);

            btnAccept.setOnClickListener(v -> {

                if (clickListener != null) {
                    clickListener.acceptRide(list.get(getAdapterPosition()));
                }
            });


            btnReject.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.rejectRide(list.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }
}