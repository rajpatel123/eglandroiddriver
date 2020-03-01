package com.eaglecabs.provider.ui.activity.profile;

import android.widget.Toast;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.model.User;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfilePresenter<V extends ProfileIView> extends BasePresenter<V> implements ProfileIPresenter<V> {

    @Override
    public void profileUpdate(Map<String, RequestBody> params, MultipartBody.Part file) {
        Observable modelObservable = APIClient.getAPIClient().profileUpdate(params, file);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                    ProfilePresenter.this.getMvpView().onSuccessUser((User) trendsResponse);
                    Toast.makeText(activity(), R.string.profile_updated_successfuly, Toast.LENGTH_SHORT).show();

                },
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void getProfile() {
        Observable modelObservable = APIClient.getAPIClient().getProfile();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
