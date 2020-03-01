package com.eaglecabs.provider.ui.activity.earnings;


import com.eaglecabs.provider.base.MvpPresenter;

public interface EarningsIPresenter<V extends EarningsIView> extends MvpPresenter<V> {
    void getEarnings();
}
