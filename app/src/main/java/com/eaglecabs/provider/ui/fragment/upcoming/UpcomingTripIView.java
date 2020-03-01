package com.eaglecabs.provider.ui.fragment.upcoming;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.HistoryList;

import java.util.List;

public interface UpcomingTripIView extends MvpView {
    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
