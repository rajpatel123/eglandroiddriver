package com.eaglecabs.provider.ui.activity.change_password;

import com.eaglecabs.provider.base.MvpView;

public interface ChangePasswordIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
