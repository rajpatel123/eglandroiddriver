package com.eaglecabs.provider.ui.activity.regsiter;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface RegisterIPresenter<V extends RegisterIView> extends MvpPresenter<V> {
    void register( @PartMap Map<String, RequestBody> params, @Part List<MultipartBody.Part> file);
    void verifyMobileAlreadyExits(Object mobile);
    void sendOTP(Object obj);

}
