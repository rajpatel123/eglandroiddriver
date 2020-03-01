package com.eaglecabs.provider.ui.activity.otp;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.MyOTP;

public interface OTPIView extends MvpView {
    void onSuccess(MyOTP otp);
    void onError(Throwable e);
}
