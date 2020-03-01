package com.eaglecabs.provider.ui.bottomsheetdialog.invoice_flow;

import com.eaglecabs.provider.base.MvpView;

public interface InvoiceDialogIView extends MvpView {
    void onSuccess(Object object);
    void onError(Throwable e);
}
