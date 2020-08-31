package com.eaglecabs.provider.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.ui.activity.splash.SplashActivity;

import org.json.JSONObject;

import retrofit2.HttpException;
import retrofit2.Response;


public abstract class BaseBottomSheetDialogFragment extends BottomSheetDialogFragment implements MvpView  {


    View view;


    public abstract int getLayoutId();


    ProgressDialog progressDialog;

    public abstract void initView(View view);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (view == null) {
            view = inflater.inflate(getLayoutId(), container, false);
            initView(view);
        }

        progressDialog = new ProgressDialog(activity());
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        return view;
    }



    @Override
    public Activity activity() {
        return getActivity();
    }

    @Override
    public void showLoading() {
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void onError(Throwable e) {
        hideLoading();
        if (e instanceof HttpException) {
            Response response = ((HttpException) e).response();
            try {
                JSONObject jObjError = new JSONObject(response.errorBody().string());
                if (jObjError.has("message"))
                    Toast.makeText(activity(), jObjError.optString("message"), Toast.LENGTH_SHORT).show();
                else if (jObjError.has("error")){
                    if (!jObjError.optString("error").equalsIgnoreCase("token_expired")) {
                        Toast.makeText(activity(), jObjError.optString("error"), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Log.e("Error", jObjError.toString());
            } catch (Exception exp) {
                Log.e("Error", exp.getMessage());
            }

            if (response.code() == 401) {
                SharedHelper.clearSharedPreferences(activity());
                activity().finishAffinity();
                startActivity(new Intent(activity(), SplashActivity.class));
            }
        }
    }
}
