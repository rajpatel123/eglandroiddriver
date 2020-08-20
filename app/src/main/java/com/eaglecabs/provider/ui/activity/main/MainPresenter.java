package com.eaglecabs.provider.ui.activity.main;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.ApiInterface;
import com.eaglecabs.provider.data.network.model.OTPResponse;
import com.eaglecabs.provider.data.network.model.TripResponse;
import com.eaglecabs.provider.data.network.model.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter<V extends MainIView> extends BasePresenter<V> implements MainIPresenter<V> {

    @Override
    public void getProfile() {
        Observable modelObservable = APIClient.getAPIClient().getProfile();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void logout(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().logout(obj);

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessLogout(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));


    }

    @Override
    public void getTrip(HashMap<String, Object> params, boolean ismannual) {

        Observable modelObservable;
        if (ismannual) {
            modelObservable = APIClient.getAPIClient().getTrip(params, 1);

        } else {
            modelObservable = APIClient.getAPIClient().getTrip(params);

        }

        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((TripResponse) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void providerAvailable(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().providerAvailable(obj);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                               @Override
                               public void accept(Object trendsResponse) throws Exception {
                                   getProfile();
                                   MainPresenter.this.getMvpView().onSuccessProviderAvailable(trendsResponse);
                               }
                           },
                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void sendFCM(JsonObject jsonObject) {

        ApiInterface FcmAPI = APIClient.getFcmRetrofit().create(ApiInterface.class);
        Observable modelObservable = FcmAPI.sendFcm(jsonObject);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessFCM(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));


    }

    @Override
    public void getTripLocationUpdate(HashMap<String, Object> params) {

        Observable modelObservable = APIClient.getAPIClient().getTrip(params, 1);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessLocationUpdate((TripResponse) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));

    }

    @Override
    public void locationUpdateServer(LatLng latLng) {

        Observable modelObservable = APIClient.getAPIClient().serverLocationUpdate(latLng.latitude, latLng.longitude);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessServerLocationUpdate(trendsResponse),
                        new Consumer() {
                            @Override
                            public void accept(Object throwable) throws Exception {
                                //SchedulePresenter.this.getMvpView().onError((Throwable) throwable);
                            }
                        });

    }

    @Override
    public void instantRideEstimateFare(HashMap<String, Object> obj) {
        getMvpView().showLoading();
        Observable modelObservable = APIClient.getAPIClient().instantRideEstimateFare(obj);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            MainPresenter.this.getMvpView().onSuccessInstant((OTPResponse) trendsResponse);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            MainPresenter.this.getMvpView().onError((Throwable) throwable);
                        });

    }


    @Override
    public void instantRideSendRequest(HashMap<String, Object> obj) {


        Observable modelObservable = APIClient.getAPIClient().instantRideSendRequest(obj);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            MainPresenter.this.getMvpView().onSuccessInstantNow((Object) trendsResponse);
                        },
                        throwable -> {
                            getMvpView().hideLoading();
                            MainPresenter.this.getMvpView().onError((Throwable) throwable);
                        });

    }
}
