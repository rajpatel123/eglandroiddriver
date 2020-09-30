package com.eaglecabs.provider.ui.activity.bankdetail;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.data.network.model.User;

public interface BankIView extends MvpView {
    void onSuccess(User bankDetails);
    void onSuccessBank(BankDetails bankDetails);
    void onError(Throwable e);
}
