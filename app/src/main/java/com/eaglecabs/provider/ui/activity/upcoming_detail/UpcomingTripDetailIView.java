package com.eaglecabs.provider.ui.activity.upcoming_detail;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.HistoryDetail;

public interface UpcomingTripDetailIView extends MvpView {
    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
