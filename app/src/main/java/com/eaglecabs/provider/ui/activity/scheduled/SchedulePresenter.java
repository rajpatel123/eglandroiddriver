package com.eaglecabs.provider.ui.activity.scheduled;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.scheduledrides.ScheduledReidesResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class SchedulePresenter<V extends ScheduledIView> extends BasePresenter<V> implements ScheduleIPresenter<V> {

    @Override
    public void getScheduledRides() {
        Observable modelObservable = APIClient.getAPIClient().getAllScheduledRides();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((List<ScheduledReidesResponse>) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void accept(Integer id, Object arrivalTime) {
        Observable modelObservable = APIClient.getAPIClient().acceptRequest("", id, arrivalTime,1);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessAccept(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }




    @Override
    public void acceptManual(Integer id, Object arrivalTime) {
        Observable modelObservable = APIClient.getAPIClient().acceptRequestManual("", id, arrivalTime,1);
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



 @Override
    public void cancelManual(Integer id) {
        Observable modelObservable = APIClient.getAPIClient().rejectRequest( id);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessCancel(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }


}
