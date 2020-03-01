package com.eaglecabs.provider.ui.activity.wallet;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.WalletResponse;

public interface WalletIView extends MvpView {
    void onSuccess(WalletResponse walletResponse);
}
