package com.eaglecabs.provider.ui.activity.wallet;

import com.eaglecabs.provider.base.MvpPresenter;

public interface WalletIPresenter<V extends WalletIView> extends MvpPresenter<V> {
    void walletHistory();
}
