package com.eaglecabs.provider.ui.activity.rate_card;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BaseActivity;
import com.eaglecabs.provider.data.network.model.RateCardService;
import com.eaglecabs.provider.ui.activity.document.DocumentActivity;
import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.eaglecabs.provider.ui.adapter.ServiceAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

public class RateCardActivity extends BaseActivity implements RatecardIView {
    @BindView(R.id.rete_card_rv)
    RecyclerView reteCardRv;
    @BindView(R.id.terms_condition)
    TextView termsCondition;
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
        setTitle(R.string.rate_card);
        adapter = new ServiceAdapter(activity(), list);
        reteCardRv.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.VERTICAL, false));
        reteCardRv.setItemAnimator(new DefaultItemAnimator());
        reteCardRv.setAdapter(adapter);
        HashMap<String, Object> serviceMap = new HashMap<>();
        serviceMap.put(SERVICE_TYPE, 2);
        presenter.services(serviceMap);

        termsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RateCardActivity.this, TermsAndConditionActivity.class));
            }
        });

    }


    @Override
    public void onSuccess(List<RateCardService> services) {
        this.list.clear();
        this.list.addAll(services);
        adapter.notifyDataSetChanged();



    }

    @Override
    public void onSuccessRequest(Object object) {

    }

}