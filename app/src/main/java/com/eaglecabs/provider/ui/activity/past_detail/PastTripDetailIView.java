package com.eaglecabs.provider.ui.activity.past_detail;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.HistoryDetail;

public interface PastTripDetailIView extends MvpView {
    void onSuccess(HistoryDetail historyDetail);
    void onError(Throwable e);
}
