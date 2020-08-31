package com.eaglecabs.provider.common;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import android.util.Log;


import com.eaglecabs.provider.R;
import com.eaglecabs.provider.data.network.model.LatLngFireBaseDB;
import com.eaglecabs.provider.data.network.model.LatLngPointModel;
import com.eaglecabs.provider.ui.activity.main.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import static com.eaglecabs.provider.base.BaseActivity.DATUM;
import static com.eaglecabs.provider.common.SharedHelper.getLocation;
import static com.eaglecabs.provider.common.SharedHelper.putLocation;
import static com.eaglecabs.provider.ui.activity.main.MainActivity.myLocationCalculationCheck;
import static com.eaglecabs.provider.MvpApplication.mLastKnownLocation;

public class GPSTrackers extends Service {

    private static final String TAG = "RRR GPSTracker";
//    private Disposable subscribe;

    String key = "";

    private double endLat = 0;
    private double endLng = 0;
    private double bearing = 0;

    private int count = 0;

    String NOTIFICATION_CHANNEL_ID = "com.example.simpleapp";

    String status_update = "Init";
    Context context;

    private LocationManager locationManager = null;
    private static final int LOCATION_INTERVAL = 10000;     //      1 sec
    private static final float LOCATION_DISTANCE = 10f;     //      10 feet

    public class LocationListener implements android.location.LocationListener {

        LocationListener(String provider) {
            Log.d(TAG, "LocationListener  " + provider);
        }

        @Override
        public void onLocationChanged(final Location location) {
            Log.d(TAG, "onLocationChanged: " + location);

            //      TODO: Commented because location updating is done here
            //      ....****....
                  EventBus.getDefault().post(location);

            if (myLocationCalculationCheck)
                locationProcessing(location);

            if (DATUM == null) return;
            if (DATUM.getStatus() != null) {
                if (DATUM.getStatus().equalsIgnoreCase("ACCEPTED")
                        || DATUM.getStatus().equalsIgnoreCase("STARTED")
                        || DATUM.getStatus().equalsIgnoreCase("ARRIVED")
                        || DATUM.getStatus().equalsIgnoreCase("PICKEDUP")) {

                    mLastKnownLocation = location;
                    saveLocationToFireBaseDB(location.getLatitude(), location.getLongitude());
                    getApplicationContext().sendBroadcast(new Intent("INTENT_FILTER"));
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println(TAG + "onStatusChanged: provider = [" + provider + "], status = [" + status + "], extras = [" + extras + "]");
        }
    }

    private void saveLocationToFireBaseDB(double lat, double lng) {
        String refPath = "loc_p_" + DATUM.getProviderId();
        System.out.println("RRR GPSTracker.saveLocationToFireBaseDB :: " + refPath);
        if (!refPath.equals("loc_p_0"))
            try {
                DatabaseReference mLocationRef = FirebaseDatabase.getInstance().getReference(refPath);
                key = key == null ? mLocationRef.push().getKey() : key;

                if (endLng == 0 || endLat == 0) {
                    endLng = lat;
                    endLat = lng;
                } else {
                    double startLat = endLat;
                    endLat = lat;
                    double startLng = endLng;
                    endLng = lng;

                    bearing = bearingBetweenLocations(startLat, startLng, endLat, endLng);
                    System.out.println("RRR bearing " + bearing);
                }

                mLocationRef.setValue(new LatLngFireBaseDB(lat, lng, bearing));
            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    private double bearingBetweenLocations(double... points) {

        double PI = 3.14159;
        double lat1 = points[0] * PI / 180;
        double long1 = points[1] * PI / 180;
        double lat2 = points[2] * PI / 180;
        double long2 = points[3] * PI / 180;
        double dLon = (long2 - long1);
        double y = Math.sin(dLon) * Math.cos(lat2);
        double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
                * Math.cos(lat2) * Math.cos(dLon);

        double brng = Math.atan2(y, x);

        brng = Math.toDegrees(brng);
        brng = (brng + 360) % 360;

        return brng;
    }


    private void locationProcessing(Location location) {
        List<LatLngPointModel> pointModels = getLocation(this);

        if (pointModels == null)
            pointModels = new ArrayList<>();

        LatLngPointModel latLngPoint = new LatLngPointModel();
        latLngPoint.setId(pointModels.size());
        latLngPoint.setLat(location.getLatitude());
        latLngPoint.setLng(location.getLongitude());
        latLngPoint.setTimeStamp(getCurrentTime());
        pointModels.add(latLngPoint);

        putLocation(this, new Gson().toJson(pointModels));

        System.out.println("RRRR TimeStamp = " + latLngPoint.getTimeStamp());

        if (getLocation(this).size() > 1)
            System.out.println("RRRR Distance = " + calculateDistance(getLocation(this)));
        else System.out.println("RRRR First Time");
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    private double calculateDistance(List<LatLngPointModel> locationEntities) {

        double totalDistance = 0;
        for (int i = 0; i + 1 < locationEntities.size(); i++)
            totalDistance += addDistance(locationEntities.get(i), locationEntities.get(i + 1));
        totalDistance = (totalDistance * (1 / 1000.0));
        totalDistance = round(totalDistance);

        //  Toast.makeText(this, "Distance ::: " + totalDistance, Toast.LENGTH_SHORT).show();

        return totalDistance;
    }

    private double addDistance(LatLngPointModel a, LatLngPointModel b) {
        Location startPoint = new Location("start");
        startPoint.setLatitude(a.getLat());
        startPoint.setLongitude(a.getLng());

        Location endPoint = new Location("end");
        endPoint.setLatitude(b.getLat());
        endPoint.setLongitude(b.getLng());

        return startPoint.distanceTo(endPoint);
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @SuppressLint("SimpleDateFormat")
    private String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        status_update = "Service Starts";

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            startMyOwnForeground();
        } else {

            Intent notificationIntent = new Intent(this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Your "+ getString(R.string.app_name)+ " vehicle is live")
                    .setContentIntent(pendingIntent).build();

           // startForeground(1, notification);
        }

        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String channelName = "Current location sharing";
        @SuppressLint("InlinedApi") NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Your "+ getString(R.string.app_name)+ " vehicle is live")
                .setPriority(NotificationManager.IMPORTANCE_MAX)
                .setCategory(Notification.CATEGORY_SERVICE)
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;

       // startForeground(2, notification);
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");

        initializeLocationManager();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "location provider requires ACCESS_FINE_LOCATION | ACCESS_COARSE_LOCATION");
            return;
        }

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_INTERVAL,
                    LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        /*if (locationManager != null) {
            for (LocationListener mLocationListener : mLocationListeners)
                try {
                    locationManager.removeUpdates(mLocationListener);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listeners, ignore", ex);
                }
        }*/

    }

    private void initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager");
        if (locationManager == null)
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

}
