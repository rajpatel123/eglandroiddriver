package com.eaglecabs.provider.ui.activity.upcoming_detail;


import com.eaglecabs.provider.base.MvpPresenter;

public interface UpcomingTripDetailIPresenter<V extends UpcomingTripDetailIView> extends MvpPresenter<V> {
    void getUpcomingDetail(Object request_id);
}
