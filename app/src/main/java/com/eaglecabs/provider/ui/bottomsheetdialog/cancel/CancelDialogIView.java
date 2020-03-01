package com.eaglecabs.provider.ui.bottomsheetdialog.cancel;

import com.eaglecabs.provider.base.MvpView;

public interface CancelDialogIView extends MvpView {

    void onSuccessCancel(Object object);
    void onError(Throwable e);
}
