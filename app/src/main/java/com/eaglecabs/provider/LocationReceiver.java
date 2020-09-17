package com.eaglecabs.provider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.eaglecabs.provider.ui.activity.main.MainPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import java.util.HashMap;

import br.com.safety.locationlistenerhelper.core.SettingsLocationTracker;

import static com.eaglecabs.provider.MvpApplication.mLastKnownLocation;
import static com.eaglecabs.provider.base.BaseActivity.DATUM;

/**
 * @author josevieira
 */
public class LocationReceiver extends BroadcastReceiver {
    MainPresenter<MainActivity> presenter = new MainPresenter<>();


    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent && intent.getAction().equals("my.action")) {
            Location locationData = (Location) intent.getParcelableExtra(SettingsLocationTracker.LOCATION_MESSAGE);
            mLastKnownLocation=locationData;
            Log.d("Location: ", "Latitude: " + locationData.getLatitude() + "Longitude:" + locationData.getLongitude());

         //   SharedHelper.putKey(this, "current_latitude", String.valueOf(mLastKnownLocation.getLatitude()));
           // SharedHelper.putKey(this, "current_longitude", String.valueOf(mLastKnownLocation.getLongitude()));
            LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            //pushNotification(latLng, mLastKnownLocation);
            presenter.locationUpdateServer(latLng);
            //send your call to api or do any things with the of location data
        }
    }






    private void pushNotification(LatLng latlng, Location location) {

        if (DATUM == null) {
            return;
        }

        if (DATUM.getStatus().equalsIgnoreCase("ACCEPTED") || DATUM.getStatus().equalsIgnoreCase("STARTED") || DATUM.getStatus().equalsIgnoreCase("ARRIVED") ||
                DATUM.getStatus().equalsIgnoreCase("PICKEDUP") || DATUM.getStatus().equalsIgnoreCase("DROPPED")) {

            JsonObject jPayload = new JsonObject();
            JsonObject jData = new JsonObject();

            jData.addProperty("latitude", latlng.latitude);
            jData.addProperty("longitude", latlng.longitude);
            jPayload.addProperty("to", "/topics/" + DATUM.getId());
            jPayload.addProperty("priority", "high");
            jPayload.add("data", jData);
            presenter.sendFCM(jPayload);
        }

        if (DATUM.getStatus().equalsIgnoreCase("PICKEDUP")) {
            if (DATUM.getIsTrack().equalsIgnoreCase("YES")) {
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("latitude", latlng.latitude);
                map1.put("longitude", latlng.longitude);
                // presenter.calculateDistance(map1, DATUM.getId());
               // serverlatlng = location;
            }
        }

    }


}