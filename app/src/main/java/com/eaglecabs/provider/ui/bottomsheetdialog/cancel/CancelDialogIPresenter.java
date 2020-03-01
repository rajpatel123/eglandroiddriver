package com.eaglecabs.provider.ui.bottomsheetdialog.cancel;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface CancelDialogIPresenter<V extends CancelDialogIView> extends MvpPresenter<V> {

    void cancelRequest(HashMap<String, Object> obj);
}
