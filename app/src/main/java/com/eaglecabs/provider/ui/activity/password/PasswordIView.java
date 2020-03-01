package com.eaglecabs.provider.ui.activity.password;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.User;

public interface PasswordIView extends MvpView {
    void onSuccess(User object);
    void onError(Throwable e);
}
