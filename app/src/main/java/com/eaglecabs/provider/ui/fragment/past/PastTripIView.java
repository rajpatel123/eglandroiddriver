package com.eaglecabs.provider.ui.fragment.past;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.HistoryList;

import java.util.List;

public interface PastTripIView extends MvpView {
    void onSuccess(List<HistoryList> historyList);
    void onError(Throwable e);
}
