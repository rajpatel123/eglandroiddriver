package com.eaglecabs.provider;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.eaglecabs.provider.ui.activity.main.MainPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

import br.com.safety.locationlistenerhelper.core.AppPreferences;
import br.com.safety.locationlistenerhelper.core.SettingsLocationTracker;

import static br.com.safety.locationlistenerhelper.core.SettingsLocationTracker.ACTION_CURRENT_LOCATION_BROADCAST;
import static com.eaglecabs.provider.MvpApplication.mLastKnownLocation;
import static com.eaglecabs.provider.base.BaseActivity.DATUM;

public class MyBackgroundLocationService extends Service {
    private static final String TAG = MyBackgroundLocationService.class.getSimpleName();
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallback;
    protected String actionReceiver;
    private AppPreferences appPreferences;
    MainPresenter<MainActivity> presenter = new MainPresenter<>();

    public MyBackgroundLocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appPreferences = new AppPreferences(getBaseContext());

        mLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Log.d(TAG, "onLocationResult: location error");
                    return;
                }

                List<Location> locations = locationResult.getLocations();

                LocationResultHelper helper = new LocationResultHelper(getApplicationContext(), locations);

              //  helper.showNotification();

                helper.saveLocationResults();
                if (locations!=null && locations.size()>0){
                    sendLocationBroadcast(locations.get(0));
                    sendCurrentLocationBroadCast(locations.get(0));
                    EventBus.getDefault().postSticky(locations.get(0));

                    LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                   // pushNotification(latLng, mLastKnownLocation);
                    presenter.locationUpdateServer(latLng);

                }

           //     Toast.makeText(getApplicationContext(), "Location received: " + locations.size(), Toast.LENGTH_SHORT).show();

            }
        };
    }




    private void sendLocationBroadcast(Location sbLocationData) {
        Intent locationIntent = new Intent();
        locationIntent.setAction("my.action");
        locationIntent.putExtra(SettingsLocationTracker.LOCATION_MESSAGE, sbLocationData);
        sendBroadcast(locationIntent);
        updateLocationToServer();
    }

    private void updateLocationToServer() {

    }

    private void sendCurrentLocationBroadCast(Location sbLocationData) {
        Intent locationIntent = new Intent();
        locationIntent.setAction(ACTION_CURRENT_LOCATION_BROADCAST);
        locationIntent.putExtra(SettingsLocationTracker.LOCATION_MESSAGE, sbLocationData);
        sendBroadcast(locationIntent);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: called");
        if (this.actionReceiver == null) {
            this.actionReceiver = this.appPreferences.getString("ACTION", "LOCATION.ACTION");
        }
        startForeground(1001, getNotification());

        getLocationUpdates();

        return START_STICKY;
    }

    private Notification getNotification() {

        NotificationCompat.Builder notificationBuilder = null;
        notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),
                MvpApplication.CHANNEL_ID)
                .setContentTitle("Eagle")
                .setContentText("Eagle service is running")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(null)
                .setAutoCancel(true);


        return notificationBuilder.build();
    }

    private void getLocationUpdates() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(60*1000);
        locationRequest.setFastestInterval(30000);

        locationRequest.setMaxWaitTime(60* 1000);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            stopSelf();
            return;
        }

        mLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
        stopForeground(true);
        mLocationClient.removeLocationUpdates(mLocationCallback);
    }
}
