package com.fitnesstracker.gymfragment;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class LocationUtils implements GoogleApiClient.ConnectionCallbacks {

    public final int RESOLUTION_REQUIRED = 1000;
    private final String TAG = LocationUtils.class.getSimpleName();
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    MyLocation myLocation;
    Activity context;
    boolean val;
    public LocationUtils(Activity context, MyLocation myLocation, boolean val){
        this.myLocation=myLocation;
        this.context=context;
        this.val=val;
        googleApiClient = new GoogleApiClient.Builder(context).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        googleApiClient.connect();

    }

    public void askToTurnOnLocation(final Activity context) {
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(1200);
        locationRequest.setFastestInterval(800);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e(TAG, "All icon_location_fmly settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade icon_location_fmly settings ");

                        try {
                            status.startResolutionForResult(context, RESOLUTION_REQUIRED);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation.locationUpdates(location);
                if (!val)
                stopUpdates();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        askToTurnOnLocation(context);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public interface MyLocation{
        void locationUpdates(Location location);
    }
    public void  stopUpdates()
    {
        googleApiClient.disconnect();
    }
}