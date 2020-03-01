package com.eaglecabs.provider.ui.activity.document;

import com.eaglecabs.provider.base.MvpView;
import com.eaglecabs.provider.ui.adapter.DocumentAdapter;

public interface DocumentIView extends MvpView {
    void onSuccess(DocumentAdapter adapter);
}
