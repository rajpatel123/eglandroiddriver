package com.eaglecabs.provider.ui.fragment.offline;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface OfflineIPresenter<V extends OfflineIView> extends MvpPresenter<V> {
    void providerAvailable(HashMap<String, Object> obj);
}
