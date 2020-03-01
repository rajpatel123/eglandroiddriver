package com.eaglecabs.provider.ui.fragment.earning;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.EarningsList;

public interface PastEarningsIView extends MvpView {
    void onSuccess(EarningsList earningsLists);
    void onError(Throwable e);
}
