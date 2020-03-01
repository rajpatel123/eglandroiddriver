package com.eaglecabs.provider.ui.activity.earnings;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.EarningsList;

public interface EarningsIView extends MvpView {
    void onSuccess(EarningsList earningsLists);
    void onError(Throwable e);
}
