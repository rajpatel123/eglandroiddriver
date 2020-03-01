package com.eaglecabs.provider.ui.activity.summary;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.Summary;

public interface SummaryIView extends MvpView {
    void onSuccess(Summary object);
    void onError(Throwable e);
}
