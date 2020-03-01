package com.eaglecabs.provider.ui.activity.splash;

import com.eaglecabs.provider.base.MvpPresenter;

public interface SplashIPresenter<V extends SplashIView> extends MvpPresenter<V> {
    void handlerCall();
}
