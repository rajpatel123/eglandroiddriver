package com.eaglecabs.provider.ui.activity.summary;


import com.eaglecabs.provider.base.MvpPresenter;

public interface SummaryIPresenter<V extends SummaryIView> extends MvpPresenter<V> {
    void getSummary();
}
