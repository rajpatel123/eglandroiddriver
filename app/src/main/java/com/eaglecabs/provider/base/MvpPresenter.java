package com.eaglecabs.provider.base;

import android.app.Activity;

import com.eaglecabs.provider.MvpApplication;

public interface MvpPresenter<V extends MvpView> {

    Activity activity();

    MvpApplication appContext();

    void attachView(V mvpView);

    void detachView();

}
