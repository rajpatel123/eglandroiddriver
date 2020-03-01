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
import android.widget.TextView;

import com.eaglecabs.provider.MvpApplication;
import com.eaglecabs.provider.R;
import com.eaglecabs.provider.common.CircularProgressBar;
import com.eaglecabs.provider.data.network.model.EarningsList;
import com.eaglecabs.provider.data.network.model.Payment;
import com.eaglecabs.provider.data.network.model.Ride;
import com.eaglecabs.provider.ui.adapter.EarningsTripAdapter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eaglecabs.provider.common.Utilities.animateTextView;

public class TodayEarning extends Fragment implements TodayEarningsIView {

    @BindView(R.id.earnings_bar)
    CircularProgressBar earningsBar;
    @BindView(R.id.target_txt)
    TextView targetTxt;
    @BindView(R.id.lblEarnings)
    TextView lblEarnings;
    @BindView(R.id.rides_rv)
    RecyclerView ridesRv;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.errorLayout)
    RelativeLayout errorLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    List<Ride> list = new ArrayList<>();
    EarningsTripAdapter adapter;
    NumberFormat numberFormat;
    TodayEarningsPresenter<TodayEarning> presenter = new TodayEarningsPresenter<>();

    ProgressDialog progressDialog;
    public TodayEarning() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_today_earning, container, false);
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
        presenter.getEarnings();
        return v;
    }


    public void onSuccess(EarningsList earningsLists) {
        hideLoading();
        list.clear();
        list.addAll(earningsLists.getRides());
        loadAdapter(earningsLists);
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

    private void loadAdapter(EarningsList earningsLists) {
        earningsBar.setProgressWithAnimation(earningsLists.getRidesCount(), 1500);
        animateTextView(0, earningsLists.getRidesCount(), Integer.parseInt(earningsLists.getTarget()), targetTxt);

        double total_price = 0;
        for (Ride ride : earningsLists.getRides()) {
            Payment payment = ride.getPayment();
            if (payment != null) {
                total_price += payment.getProviderPay();
            }
        }
        lblEarnings.setText(numberFormat.format(total_price));

        if (list.size() > 0) {
            EarningsTripAdapter adapter = new EarningsTripAdapter(list, getActivity());
            ridesRv.setAdapter(adapter);
            ridesRv.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
        } else {
            ridesRv.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }

    }




}
