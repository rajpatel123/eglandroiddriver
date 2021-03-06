package com.eaglecabs.provider.ui.activity.regsiter;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.models.CityResponse;
import com.eaglecabs.provider.data.network.model.MyOTP;
import com.eaglecabs.provider.data.network.model.User;

public interface RegisterIView extends MvpView {
    void onSuccess(User user);
    void onSuccess(MyOTP otp);
   void onSuccess(CityResponse cityResponse);
    void onError(Throwable e);
}
