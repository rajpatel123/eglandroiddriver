package com.eaglecabs.provider.ui.fragment.upcoming;


import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.models.UpcomingAcceptedTripsModel;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.model.HistoryList;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpcomingTripPresenter<V extends UpcomingTripIView> extends BasePresenter<V> implements UpcomingTripIPresenter<V> {

    @Override
    public void getUpcoming() {
        Observable modelObservable = APIClient.getAPIClient().getAcceptedUpcoming();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<UpcomingAcceptedTripsModel>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }
}
