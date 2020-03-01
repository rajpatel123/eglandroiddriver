package com.eaglecabs.provider.ui.fragment.earning;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.model.EarningsList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TodayEarningsPresenter<V extends TodayEarningsIView> extends BasePresenter<V> implements TodayEarningsIPresenter<V> {

    @Override
    public void getEarnings() {
        Observable modelObservable = APIClient.getAPIClient().getEarnings();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((EarningsList) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

}
