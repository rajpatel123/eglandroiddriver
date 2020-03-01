package com.eaglecabs.provider.ui.fragment.earning;


import com.eaglecabs.provider.base.MvpPresenter;

public interface PastEarningsIPresenter<V extends PastEarningsIView> extends MvpPresenter<V> {
    void getEarningsAll();
}
