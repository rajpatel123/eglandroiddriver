package com.eaglecabs.provider.common.kalman;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.location.LocationResult;

public class MyLocationService extends BroadcastReceiver {
    public static final String ACTION_PROCESS_LOCATION_UPDATE="com.eaglecabs.provider.common.kalman.ACTION_PROCESS_LOCATION_UPDATE";


    @Override
    public void onReceive(Context context, Intent intent) {
if (intent!=null){
     final String action = intent.getAction();
     if (ACTION_PROCESS_LOCATION_UPDATE.equals(action)){
         LocationResult locationResult = LocationResult.extractResult(intent);
         if (locationResult!=null){
             Location location =    locationResult.getLastLocation();

         }
     }
}


    }
}
