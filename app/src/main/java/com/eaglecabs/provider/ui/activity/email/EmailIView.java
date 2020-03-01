package com.eaglecabs.provider.ui.activity.email;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.User;

public interface EmailIView extends MvpView {
    void onSuccess(User token);
    void onError(Throwable e);
}
