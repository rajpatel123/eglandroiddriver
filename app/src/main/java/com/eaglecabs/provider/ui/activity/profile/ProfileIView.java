package com.eaglecabs.provider.ui.activity.profile;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.User;

public interface ProfileIView extends MvpView {
    void onSuccess(User user);
    void onSuccessUser(User user);
    void onError(Throwable e);
}
