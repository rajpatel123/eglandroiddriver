package com.eaglecabs.provider.ui.activity.bankdetail;

import android.widget.Toast;

import com.eaglecabs.provider.R;
import com.eaglecabs.provider.base.BasePresenter;
import com.eaglecabs.provider.data.network.APIClient;
import com.eaglecabs.provider.data.network.model.User;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class BankPresenter<V extends BankIView> extends BasePresenter<V> implements BankIPresenter<V> {

    @Override
    public void bankDetailUpdate(HashMap<String, RequestBody> params, @Part MultipartBody.Part filename) {
        Observable modelObservable = APIClient.getAPIClient().addUpdateBankDetail(params,filename);
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> {
                    BankPresenter.this.getMvpView().onSuccessBank((BankDetails) trendsResponse);
                    Toast.makeText(activity(), R.string.bank_updated_successfuly, Toast.LENGTH_SHORT).show();

                },
                        throwable -> getMvpView().onError((Throwable) throwable));
    }

    @Override
    public void getBank() {
        Observable modelObservable = APIClient.getAPIClient().getBank();
        modelObservable.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trendsResponse -> getMvpView().onSuccess((User) trendsResponse),
                        throwable -> getMvpView().onError((Throwable) throwable));
    }
}
