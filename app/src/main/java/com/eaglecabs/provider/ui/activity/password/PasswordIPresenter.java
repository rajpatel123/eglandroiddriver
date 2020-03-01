package com.eaglecabs.provider.ui.activity.password;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;

public interface PasswordIPresenter<V extends PasswordIView> extends MvpPresenter<V> {
    void login(HashMap<String, Object> obj);
}
