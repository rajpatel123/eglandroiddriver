package com.eaglecabs.provider.ui.activity.sociallogin;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.Token;

public interface SocialLoginIView extends MvpView {
    void onSuccess(Token token);
    void onError(Throwable e);
}
