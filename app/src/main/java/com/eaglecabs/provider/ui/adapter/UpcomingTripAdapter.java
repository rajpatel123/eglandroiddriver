package com.eaglecabs.provider.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.data.models.UpcomingAcceptedTripsModel;

import java.util.List;

/**
 * Created by suthakar@appoets.com on 28-06-2018.
 */
public class UpcomingTripAdapter extends RecyclerView.Adapter<UpcomingTripAdapter.MyViewHolder> {

    private List<UpcomingAcceptedTripsModel> list;
    private Context context;

    private ClickListener clickListener;

    public UpcomingTripAdapter(List<UpcomingAcceptedTripsModel> list, Context con) {
        this.list = list;
        this.context = con;
    }

    public void setList(List<UpcomingAcceptedTripsModel> list) {
        this.list = list;
    }

    public void setClickListener(UpcomingTripAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        void redirectClick(UpcomingAcceptedTripsModel historyList);
        void cancelRide(UpcomingAcceptedTripsModel historyList, int pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_upcoming_trip, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UpcomingAcceptedTripsModel historyList = list.get(position);

        holder.lblDate.setText(historyList.getOutLeave());
        holder.lblBookingid.setText(historyList.getBookingId());
        holder.lblSeviceName.setText(historyList.getServiceRequired());

        Glide.with(context).load(historyList.getRouteKey()).apply(RequestOptions.placeholderOf(R.drawable.ic_launcher_background).dontAnimate().error(R.drawable.ic_launcher_background)).into(holder.staticMap);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView itemView;
        private TextView lblDate, lblBookingid, lblSeviceName;
        private ImageView staticMap,avatar;
        private Button btnCancelRide;

        private MyViewHolder(View view) {
            super(view);

            itemView = view.findViewById(R.id.item_view);
            lblDate = view.findViewById(R.id.lblDate);
            lblBookingid = view.findViewById(R.id.lblBookingid);
            lblSeviceName = view.findViewById(R.id.lblSeviceName);
            staticMap = view.findViewById(R.id.static_map);
            avatar = view.findViewById(R.id.avatar);

            btnCancelRide = view.findViewById(R.id.btnCancelRide);

            itemView.setOnClickListener(v -> {

                if (clickListener != null) {
                    clickListener.redirectClick(list.get(getAdapterPosition()));
                }
            });


            btnCancelRide.setOnClickListener(v -> {

                if (clickListener != null) {
                    clickListener.cancelRide(list.get(getAdapterPosition()), getAdapterPosition());
                }
            });


        }
    }
}