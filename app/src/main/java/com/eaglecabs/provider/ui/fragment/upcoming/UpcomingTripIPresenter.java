package com.eaglecabs.provider.ui.fragment.upcoming;


import com.eaglecabs.provider.base.MvpPresenter;

public interface UpcomingTripIPresenter<V extends UpcomingTripIView> extends MvpPresenter<V> {
    void getUpcoming();
}
