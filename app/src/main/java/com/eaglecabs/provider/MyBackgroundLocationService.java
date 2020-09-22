package com.eaglecabs.provider;

import android.Manifest;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.eaglecabs.provider.common.ConnectivityReceiver;
import com.eaglecabs.provider.common.SharedHelper;
import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.eaglecabs.provider.ui.activity.main.MainPresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.safety.locationlistenerhelper.core.AppPreferences;
import br.com.safety.locationlistenerhelper.core.AppUtils;
import br.com.safety.locationlistenerhelper.core.SettingsLocationTracker;

import static br.com.safety.locationlistenerhelper.core.SettingsLocationTracker.ACTION_CURRENT_LOCATION_BROADCAST;
import static com.eaglecabs.provider.MvpApplication.mLastKnownLocation;

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
                if (locations != null && locations.size() > 0) {
                    mLastKnownLocation=locations.get(0);

                    SharedHelper.putKey(MyBackgroundLocationService.this, "current_latitude", String.valueOf(locations.get(0).getLatitude()));
                    SharedHelper.putKey(MyBackgroundLocationService.this, "current_longitude", String.valueOf(locations.get(0).getLongitude()));


                    if (SharedHelper.getKey(MyBackgroundLocationService.this, "tripStatus").equalsIgnoreCase("PICKEDUP")){


                        if (SharedHelper.getDoubleKey(MyBackgroundLocationService.this, "lastLat")>-1.0){
                            getDistance(locations.get(0).getLatitude(), locations.get(0).getLongitude(),
                                    SharedHelper.getDoubleKey(MyBackgroundLocationService.this, "lastLat"),
                                    SharedHelper.getDoubleKey(MyBackgroundLocationService.this, "lastLong")
                            );
                        }else{
                            SharedHelper.putKey(MyBackgroundLocationService.this, "lastLat", (float) locations.get(0).getLatitude());
                            SharedHelper.putKey(MyBackgroundLocationService.this, "lastLong", (float) locations.get(0).getLongitude());

                        }

                    }


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


    private void getDistance(double currentLat2, double currentLong2, float mallLat2, float mallLong2) {
        if (ConnectivityReceiver.isConnected()) {
            new AsyncTask<Void, Void, Double>() {
                @Override
                protected Double doInBackground(Void... voids) {

                    Location loc1 = new Location("");
                    loc1.setLatitude(currentLat2);
                    loc1.setLongitude(currentLong2);

                    Location loc2 = new Location("");
                    loc2.setLatitude(mallLat2);
                    loc2.setLongitude(mallLong2);

                    return Double.valueOf(loc1.distanceTo(loc2));


//                   double theta = lon1 - lon2;
//                   double dist = Math.sin(deg2rad(lat1))
//                           * Math.sin(deg2rad(lat2))
//                           + Math.cos(deg2rad(lat1))
//                           * Math.cos(deg2rad(lat2))
//                           * Math.cos(deg2rad(theta));
//                   dist = Math.acos(dist);
//                   dist = rad2deg(dist);
//                   dist = dist * 60 * 1.1515;
//                   return dist;
                }

                @Override
                protected void onPostExecute(Double dist) {
                    super.onPostExecute(dist);
                    if (dist > 300) {
                        double distance =  SharedHelper.getDoubleKey(MyBackgroundLocationService.this, "tripDistance");
                        distance = distance + dist;

                        SharedHelper.putKey(MyBackgroundLocationService.this, "tripDistance", (float) distance);

                        SharedHelper.putKey(MyBackgroundLocationService.this, "lastLat", mallLat2);
                        SharedHelper.putKey(MyBackgroundLocationService.this, "lastLong", mallLong2);

                        String data ="Latitude: "+ String.valueOf(mallLat2)+ " Longitude : "+ String.valueOf(mallLong2)+" Distance gap "+dist+"\n";
                        SharedHelper.putKey(MyBackgroundLocationService.this, ""+System.currentTimeMillis(), data);

                        // AppUtils.writeToFile(data, MyBackgroundLocationService.this);


                    }


                    //SharedHelper.putKey(MainActivity.this, "totalDist", String.valueOf(TRIPDISTANCE));

                }
            }.execute();
        }

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
                .setAutoCancel(true);

        return notificationBuilder.build();
    }

    private void getLocationUpdates() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(60 * 1000);
        locationRequest.setFastestInterval(30000);

        locationRequest.setMaxWaitTime(60 * 1000);

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
