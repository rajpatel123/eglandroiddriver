package com.eaglecabs.provider.ui.activity.rate_card;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface RateCardIPresenter <V extends RatecardIView> extends MvpPresenter<V> {

    void services(HashMap<String, Object> serviceMap);
    void sendRequest(HashMap<String, Object> parms);
    void estimateFare(HashMap<String, Object> parms);
}
