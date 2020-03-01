package com.eaglecabs.provider.ui.fragment.status_flow;

import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StatusFlowPresenter<V extends StatusFlowIView> extends BasePresenter<V> implements StatusFlowIPresenter<V> {

    @Override
    public void statusUpdate(HashMap<String, Object> obj, Integer id) {
        Observable modelObservable = APIClient.getAPIClient().updateRequest(obj, id);
        getMvpView().showLoading();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                            getMvpView().hideLoading();
                            StatusFlowPresenter.this.getMvpView().onSuccess(trendsResponse);
                        },
                        (Consumer) throwable -> {
                            getMvpView().hideLoading();
                            StatusFlowPresenter.this.getMvpView().onError((Throwable) throwable);
                        });
    }

}
