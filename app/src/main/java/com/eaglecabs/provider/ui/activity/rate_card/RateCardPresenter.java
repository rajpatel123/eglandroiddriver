package com.eaglecabs.provider.ui.activity.rate_card;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.model.RateCardService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public  class RateCardPresenter<V extends RatecardIView> extends BasePresenter<V> implements RateCardIPresenter<V> {

    @Override
    public void services(HashMap<String, Object> serviceMap) {

        Observable modelObservable = APIClient.getAPIClient().services(serviceMap);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<RateCardService>) trendsResponse),

                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void sendRequest(HashMap<String, Object> parms) {

    }

    @Override
    public void estimateFare(HashMap<String, Object> parms) {

    }

}
