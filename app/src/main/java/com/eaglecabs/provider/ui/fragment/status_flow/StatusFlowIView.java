package com.eaglecabs.provider.ui.fragment.status_flow;

import com.eaglecabs.provider.base.MvpView;

public interface StatusFlowIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
