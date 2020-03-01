package com.eaglecabs.provider.ui.activity.forgot_password;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface ForgotIPresenter<V extends ForgotIView> extends MvpPresenter<V> {
    void forgot(HashMap<String, Object> obj);
}
