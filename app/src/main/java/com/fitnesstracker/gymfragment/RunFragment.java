package com.fitnesstracker.gymfragment;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fitnesstracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class RunFragment extends Fragment implements OnMapReadyCallback, LocationUtils.MyLocation, View.OnClickListener {
    LocationUtils locationUtils;
    private GoogleMap mMap;
    private ArrayList<LatLng> points; //added
    Polyline line;
    boolean first =true;
    LatLng start;
    LatLng currentLoc;
    ImageView imvCurrent;
    TextView bStop;
    TextView tvTotalTime, tvAvgTime;
    //
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    Handler handler;
    int Seconds, Minutes, MilliSeconds, hour, sec = 0;
    double distance = 0.0;
    long pauseTime;
    CountDownTimer countDownTimer;
    double calories;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.gym_activitypage_fragment,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        imvCurrent = view.findViewById(R.id.imvCurrent);
        tvTotalTime = view.findViewById(R.id.tvTotalTime);
        tvAvgTime = view.findViewById(R.id.tvAvgTime);
        points = new ArrayList<LatLng>();
        bStop = view.findViewById(R.id.bStop);
        handler = new Handler();
        bStop.setOnClickListener(this);
        imvCurrent.setOnClickListener(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        locationUtils = new LocationUtils(getActivity(),this,true);
            StartTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imvCurrent:
                if (currentLoc != null)
                    camera(currentLoc);
                break;
            case R.id.bStop:
                /*Bundle bundle = new Bundle();
                    bundle.putString("dist", distance + "");
                    bundle.putInt("tTime", sec);
                    bundle.putString("speed", tvAvgTime.getText().toString());
                    bundle.putString("cal", calories + "");
                Fragment fragment = new CongratulationFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.runContainer, fragment)
                        .commit();*/
                break;
        }

    }
    private void redrawLine() {
        distance = 0;
        mMap.clear();
        PolylineOptions options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
            if (points.size() != (i + 1)) {
                distance += distance(points.get(i).latitude, points.get(i).longitude, points.get(i + 1).latitude, points.get(i + 1).longitude, "K");
            }
        }
        calories = calculateCalories(distance, Minutes);
        double speed = (distance * 1000) / sec;
        if (String.valueOf(speed).matches("^[a-zA-Z]*$"))
            tvAvgTime.setText("0.0");
        else
            tvAvgTime.setText(String.format("%.1f", speed));

        line = mMap.addPolyline(options); //add Polyline
        Log.e("test",calories+" "+distance+" ");
    }
    private double calculateCalories(double velocity, long calTime) {
        double weight = Double.parseDouble("55");
        double height = Double.parseDouble("20");
        double calories = (0.035 * weight) + ((velocity * 2) / height) * (0.029) * (weight);
        return calories * calTime;

    }

    @Override
    public void locationUpdates(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        points.add(latLng); //added
        currentLoc = latLng;
        redrawLine(); //added
        currentMarker(currentLoc); //add Marker in current position
        if (first) {
            start = latLng;
            camera(start);
            first = false;
        }
        startMarker(start);
    }

    private void camera(LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
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

}
