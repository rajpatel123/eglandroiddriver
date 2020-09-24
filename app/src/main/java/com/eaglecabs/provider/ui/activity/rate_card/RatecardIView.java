package com.eaglecabs.provider.ui.activity.rate_card;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.RateCardService;

import java.util.List;

public interface RatecardIView extends MvpView {
    void onSuccess(List<RateCardService> services);

    void onSuccessRequest(Object object);

    //void onSuccess(EstimateFare estimateFare);
}
