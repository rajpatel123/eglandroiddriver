package com.eaglecabs.provider.ui.activity.forgot_password;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.ForgotResponse;

public interface ForgotIView extends MvpView {
    void onSuccess(ForgotResponse forgotResponse);
    void onError(Throwable e);
}
