package com.eaglecabs.provider.ui.bottomsheetdialog.cancel;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CancelDialogPresenter<V extends CancelDialogIView> extends BasePresenter<V> implements CancelDialogIPresenter<V> {

    @Override
    public void cancelRequest(HashMap<String, Object> obj) {
        Observable modelObservable = APIClient.getAPIClient().cancelRequest(obj);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccessCancel(trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
