package com.eaglecabs.provider.ui.activity.summary;


import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.model.Summary;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SummaryPresenter<V extends SummaryIView> extends BasePresenter<V> implements SummaryIPresenter<V> {

    @Override
    public void getSummary() {
        Observable modelObservable = APIClient.getAPIClient().getSummary("");
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((Summary) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }
}
