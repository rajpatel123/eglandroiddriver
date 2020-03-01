package com.eaglecabs.provider.ui.fragment.incoming_request;

import com.eaglecabs.provider.base.MvpPresenter;

public interface IncomingRequestIPresenter<V extends IncomingRequestIView> extends MvpPresenter<V> {
    void accept(Integer id, Object arrivalTime);
    void cancel(Integer id);
}
