package com.eaglecabs.provider.ui.activity.document;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.List;

import okhttp3.MultipartBody;

public interface DocumentIPresenter<V extends DocumentIView> extends MvpPresenter<V> {
    void documents();
    void documents(List<MultipartBody.Part> documents);
}
