package com.eaglecabs.provider.ui.activity.rate_card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.common.Constants;
import com.eaglecabs.provider.data.network.model.Notification;
import com.eaglecabs.provider.data.network.model.RateCardService;
import com.eaglecabs.provider.ui.activity.notification.NotificationActivity;
import com.eaglecabs.provider.ui.activity.notification.NotificationPresenter;
import com.eaglecabs.provider.ui.adapter.NotificationAdapter;
import com.eaglecabs.provider.ui.adapter.ServiceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RateCardActivity extends BaseActivity implements RatecardIView {
    @BindView(R.id.rete_card_rv)
    RecyclerView reteCardRv;
    String SERVICE_TYPE = "service_type";

    List<RateCardService> list = new ArrayList<>();
    ServiceAdapter adapter;
    RateCardPresenter<RateCardActivity> presenter = new RateCardPresenter<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_rate_card;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        presenter.attachView(this);
        adapter = new ServiceAdapter(activity(), list);
        reteCardRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        reteCardRv.setItemAnimator(new DefaultItemAnimator());
        reteCardRv.setAdapter(adapter);
        HashMap<String, Object> serviceMap = new HashMap<>();
        serviceMap.put(SERVICE_TYPE, 2);
        presenter.services(serviceMap);

        // notifications();

    }

    @Override
    public void onSuccess(List<RateCardService> services) {
        this.list.clear();
        this.list.addAll(services);
        adapter.notifyDataSetChanged();
        System.out.println("LOGGER :"+services.get(0).getName());
        System.out.println("LOGGER :"+services.get(0).getRentalKmPrice());
        System.out.println("LOGGER :"+services.get(0).getRentalMinutePrice());

    }

    @Override
    public void onSuccessRequest(Object object) {

    }

}