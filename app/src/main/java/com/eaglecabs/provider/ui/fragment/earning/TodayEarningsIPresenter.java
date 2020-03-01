package com.eaglecabs.provider.ui.fragment.earning;


import com.eaglecabs.provider.base.MvpPresenter;

public interface TodayEarningsIPresenter<V extends TodayEarningsIView> extends MvpPresenter<V> {
    void getEarnings();
}
