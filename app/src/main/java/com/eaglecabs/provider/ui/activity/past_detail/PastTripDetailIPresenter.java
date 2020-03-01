package com.eaglecabs.provider.ui.activity.past_detail;


import com.eaglecabs.provider.base.MvpPresenter;

public interface PastTripDetailIPresenter<V extends PastTripDetailIView> extends MvpPresenter<V> {
    void getPastTripDetail(Object request_id);
}
