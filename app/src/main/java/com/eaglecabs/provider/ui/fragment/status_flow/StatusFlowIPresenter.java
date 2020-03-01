package com.eaglecabs.provider.ui.fragment.status_flow;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface StatusFlowIPresenter<V extends StatusFlowIView> extends MvpPresenter<V> {
    void statusUpdate(HashMap<String, Object> obj, Integer id);
}
