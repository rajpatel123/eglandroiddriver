package com.eaglecabs.provider.ui.fragment.earning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.eaglecabs.provider.MvpApplication;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.data.network.model.EarningsList;
import com.eaglecabs.provider.data.network.model.Ride;
import com.eaglecabs.provider.ui.adapter.EarningsPastTripAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PastEarning extends Fragment implements PastEarningsIView {

    @BindView(R.id.rides_rv)
    RecyclerView ridesRv;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    List<Ride> list = new ArrayList<>();
    NumberFormat numberFormat;
    PastEarningsPresenter<PastEarning> presenter = new PastEarningsPresenter<>();

    ProgressDialog progressDialog;
    public PastEarning() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_past_earning, container, false);
        ButterKnife.bind(this,v);
        presenter.attachView(this);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        numberFormat = MvpApplication.getNumberFormat();
        ridesRv.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        ridesRv.setItemAnimator(new DefaultItemAnimator());
        ridesRv.setHasFixedSize(true);

        showLoading();
        presenter.getEarningsAll();
        return v;
    }


    public void onSuccess(EarningsList earningsLists) {
        hideLoading();
        list.clear();
        list.addAll(earningsLists.getRides());
        // abve function to return ride history
        loadAdapter();
    }

    @Override
    public Activity activity() {
        return getActivity();
    }

    public void showLoading() {
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.dismiss();
    }

    public void onError(Throwable e) {
        hideLoading();
    }

    private void loadAdapter() {
        if (list.size() > 0) {
            EarningsPastTripAdapter adapter = new EarningsPastTripAdapter(list, getActivity());
            ridesRv.setAdapter(adapter);
            ridesRv.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
        } else {
            ridesRv.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }

    }

}
