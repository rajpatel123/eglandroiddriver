package com.eaglecabs.provider.ui.fragment.past;


import com.eaglecabs.provider.base.MvpPresenter;

public interface PastTripIPresenter<V extends PastTripIView> extends MvpPresenter<V> {
    void getHistory();
}
