package com.eaglecabs.provider.ui.activity.scheduled;

import com.eaglecabs.provider.base.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.HashMap;

public interface ScheduleIPresenter<V extends ScheduledIView> extends MvpPresenter<V> {

    void getScheduledRides();
    void accept(Integer id, Object arrivalTime);
    void acceptManual(Integer id, Object arrivalTime);
    void cancel(Integer id);
    void cancelManual(Integer id);
}
