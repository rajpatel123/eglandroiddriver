package com.eaglecabs.provider.ui.activity.notification;


import com.eaglecabs.provider.base.MvpPresenter;

public interface NotificationIPresenter<V extends NotificationIView> extends MvpPresenter<V> {
    void notifications();
}
