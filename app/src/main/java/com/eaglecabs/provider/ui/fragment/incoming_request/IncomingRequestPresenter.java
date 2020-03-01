package com.eaglecabs.provider.ui.fragment.incoming_request;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IncomingRequestPresenter<V extends IncomingRequestIView> extends BasePresenter<V> implements IncomingRequestIPresenter<V> {

    @Override
    public void accept(Integer id, Object arrivalTime) {
        Observable modelObservable = APIClient.getAPIClient().acceptRequest("", id, arrivalTime);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessAccept(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void cancel(Integer id) {
        Observable modelObservable = APIClient.getAPIClient().rejectRequest( id);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessCancel(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
