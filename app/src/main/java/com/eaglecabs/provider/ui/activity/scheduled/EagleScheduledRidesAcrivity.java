package com.eaglecabs.provider.ui.activity.scheduled;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.data.network.model.HistoryList;
import com.eaglecabs.provider.data.scheduledrides.ScheduledReidesResponse;
import com.eaglecabs.provider.data.scheduledrides.ScheduledTripsAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class EagleScheduledRidesAcrivity extends BaseActivity implements ScheduledIView, ScheduledTripsAdapter.ClickListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.scheduled_tripsRV)
    RecyclerView scheduled_tripsRV;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Unbinder unbinder;
    List<HistoryList> list = new ArrayList<>();

    SchedulePresenter<ScheduledIView> presenter = new SchedulePresenter<>();
    private ScheduledTripsAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_eagle_scheduled_rides_acrivity;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Scheduled Trips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        scheduled_tripsRV.setLayoutManager(mLayoutManager);
        scheduled_tripsRV.setItemAnimator(new DefaultItemAnimator());
        showLoading();
        presenter.getScheduledRides();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoading();
        presenter.getScheduledRides();

    }

    @Override
    public void onSuccess(List<ScheduledReidesResponse> scheduledReidesResponses) {
        hideLoading();
        Log.d("Data", "" + new Gson().toJson(scheduledReidesResponses));

        if (scheduledReidesResponses != null && scheduledReidesResponses.size() > 0) {
            adapter = new ScheduledTripsAdapter(scheduledReidesResponses, this);
            adapter.setClickListener(this);
            scheduled_tripsRV.setAdapter(adapter);
            scheduled_tripsRV.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
        } else {
            scheduled_tripsRV.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onSuccessAccept(Object responseBody) {
        presenter.getScheduledRides();
    }

    @Override
    public void onSuccessCancel(Object object) {
        presenter.getScheduledRides();
    }


    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable e) {
        Log.d("Data", "" + e.getMessage());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void acceptRide(ScheduledReidesResponse scheduledReidesResponse) {
        showLoading();
        presenter.acceptManual(scheduledReidesResponse.getId(), 10);
    }

    @Override
    public void rejectRide(ScheduledReidesResponse shScheduledReidesResponse, int pos) {
        showLoading();
        presenter.cancelManual(shScheduledReidesResponse.getId());
    }
}
