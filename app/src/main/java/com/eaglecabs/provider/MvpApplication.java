package com.eaglecabs.provider;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.eaglecabs.provider.common.ConnectivityReceiver;
import com.eaglecabs.provider.common.SharedHelper;

import io.fabric.sdk.android.Fabric;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class MvpApplication extends Application {

    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 2;
    public static float DEFAULT_ZOOM = 15;
    public static Location mLastKnownLocation;
    public static final int PERMISSIONS_REQUEST_PHONE = 4;
    public static final int PICK_LOCATION_REQUEST_CODE = 3;
    private static MvpApplication mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Stetho.initializeWithDefaults(this);
        mInstance = this;

    }

    public static synchronized MvpApplication getInstance() {
        return mInstance;
    }

    public static void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    public static NumberFormat getNumberFormat() {
        String currencyCode = SharedHelper.getKey(getInstance(), "currency_code", "INR");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        numberFormat.setCurrency(Currency.getInstance(currencyCode));
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

}
