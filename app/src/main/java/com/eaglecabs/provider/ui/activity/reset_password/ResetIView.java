package com.eaglecabs.provider.ui.activity.reset_password;

import com.eaglecabs.provider.base.MvpView;

public interface ResetIView extends MvpView{
    void onSuccess(Object object);
    void onError(Throwable e);
}
