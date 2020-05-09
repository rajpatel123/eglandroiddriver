package com.eaglecabs.provider.ui.activity.scheduled;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.scheduledrides.ScheduledReidesResponse;

import java.util.List;


public interface ScheduledIView extends MvpView {
    void onSuccess(List<ScheduledReidesResponse> responseBody);
    void onSuccessAccept(Object responseBody);
    void onSuccessCancel(Object object);

}
