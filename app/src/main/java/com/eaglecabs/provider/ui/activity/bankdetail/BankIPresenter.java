package com.eaglecabs.provider.ui.activity.bankdetail;

import com.eaglecabs.provider.base.MvpPresenter;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface BankIPresenter<V extends BankIView> extends MvpPresenter<V> {
    void bankDetailUpdate(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part filename);
    void getBank();
}
