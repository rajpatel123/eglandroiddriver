package com.eaglecabs.provider.ui.activity.notification;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.Notification;

import java.util.List;

public interface NotificationIView extends MvpView {
    void onSuccess(List<Notification> notifications);
}
