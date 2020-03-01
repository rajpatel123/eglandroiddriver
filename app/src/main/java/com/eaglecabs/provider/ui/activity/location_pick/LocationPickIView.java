package com.eaglecabs.provider.ui.activity.location_pick;


import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.AddressResponse;

/**
 * Created by santhosh@appoets.com on 19-05-2018.
 */
public interface LocationPickIView extends MvpView {

    void onSuccess(AddressResponse address);
    void onError(Throwable e);
}
