package com.fitnesstracker.gymfragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fitnesstracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RunFragment extends Fragment implements View.OnClickListener , OnMapReadyCallback, LocationUtils.MyLocation {
    private final int REQUEST_LOCATION_PERMISSION = 1;
    LocationUtils locationUtils;
    Marker marker;
    GoogleMap gmap;

    private GoogleMap mMap;
    private double StartTime;
    Handler handler;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.run_fragment);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.run_fragment,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

       /* initializeMap();*/
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
        }else{
            mapFragment.getMapAsync(this);

        }
        return view;


    }
   /* private void initializeMap() {
        if (gmap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                    .findFragmentById(R.id.map);
            if(mapFragment!=null){
                mapFragment.getMapAsync(this);
            }

            // check if map is created successfully or not
            if (null== gmap) {
                Toast.makeText(getActivity(), "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;

        // Add a marker in Sydney and move the camera

        gmap.setMyLocationEnabled(true);
        LatLng sydney = new LatLng(-34, 151);
        /*mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
        locationUtils = new LocationUtils(getActivity(),this,true);
     
        if (getIntent().getStringExtra("type").equalsIgnoreCase("free")) {
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
        } else if (getIntent().getStringExtra("type").equalsIgnoreCase("coach")) {
            if (getIntent().getBooleanExtra("fixed", false)) {
                int time = Integer.parseInt(getIntent().getStringExtra("min")) * 60 * 1000;
                startCountDownTimer(time);
            } else {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        } else {
            int time = Integer.parseInt(getIntent().getStringExtra("min").replace(" mintues", "")) * 60 * 1000;
            startCountDownTimer(time);
        }
    }

    private Intent getIntent() {
    }

    private void redrawLine() {
        distance = 0;
        mMap.clear();  //clears all Markers and Polylines
        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
            if (points.size() != (i + 1)) {
                distance += distance(points.get(i).latitude, points.get(i).longitude, points.get(i + 1).latitude, points.get(i + 1).longitude, "K");
            }
        }
        //if (getIntent().getStringExtra("type").equalsIgnoreCase("coach") && !getIntent().getBooleanExtra("fixed", false)) {
        calories = calculateCalories(distance, Minutes);


        double speed = (distance * 1000) / sec;
        if (String.valueOf(speed).matches("^[a-zA-Z]*$"))
            tvAvgTime.setText("0.0");
        else
            tvAvgTime.setText(String.format("%.1f", speed));

        line = mMap.addPolyline(options); //add Polyline
    }

    private void redrawLine() {
        distance = 0;
        mMap.clear();  //clears all Markers and Polylines
        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
            if (points.size() != (i + 1)) {
                distance += distance(points.get(i).latitude, points.get(i).longitude, points.get(i + 1).latitude, points.get(i + 1).longitude, "K");
            }
        }
        //if (getIntent().getStringExtra("type").equalsIgnoreCase("coach") && !getIntent().getBooleanExtra("fixed", false)) {
        calories = calculateCalories(distance, Minutes);


        double speed = (distance * 1000) / sec;
        if (String.valueOf(speed).matches("^[a-zA-Z]*$"))
            tvAvgTime.setText("0.0");
        else
            tvAvgTime.setText(String.format("%.1f", speed));

        line = mMap.addPolyline(options); //add Polyline
    }

    private double calculateCalories(double velocity, long calTime) {
        double weight = Double.parseDouble(helper.getWeight());
        double height = Double.parseDouble(helper.getHeight());
        double calories = (0.035 * weight) + ((velocity * 2) / height) * (0.029) * (weight);
        return calories * Minutes;


        @Override
        public void locationUpdates (Location location){
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            if (marker == null) {
                // marker = this.gmap.addMarker(new MarkerOptions().position(latLng).title("marker"));
                CameraPosition googlePlex = CameraPosition.builder()
                        .target(latLng)
                        .zoom(25)
                        .bearing(0)
                        .tilt(0)
                        .build();
                this.gmap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
            } else {
                marker.setPosition(latLng);
            }
        }
        private void camera (LatLng latLng){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        }
    }

    private void startMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("start");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        mMap.addMarker(markerOptions);
    }

    private void currentMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.addMarker(markerOptions);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationUtils.stopUpdates();
        unregisterReceiver(receiver);
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "M") {
            dist = dist * 0.8684;
        }
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            sec = Seconds;
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            hour = Minutes / 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            tvTotalTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", Minutes) + ":"
                    + String.format("%02d", Seconds));
            handler.postDelayed(this, 0);
        }

    };

    private void startCountDownTimer(final long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Seconds = (int) (millisUntilFinished / 1000);
                int time = Integer.parseInt(getIntent().getStringExtra("min").replace(" mintues", "")) * 60;
                sec = time - Seconds;
                Log.e("shubham",sec+" "+time+" "+Seconds);
                Minutes = Seconds / 60;
                Seconds = Seconds % 60;
                hour = Minutes / 60;
                tvTotalTime.setText(String.format("%02d", hour) + ":" + String.format("%02d", Minutes) + ":"
                        + String.format("%02d", Seconds));
                pauseTime = millisUntilFinished / 1000;

            }

            @Override
            public void onFinish() {

            }

        }.start();
        countDownTimer.start();
    }


}
