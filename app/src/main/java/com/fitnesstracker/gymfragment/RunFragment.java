package com.fitnesstracker.gymfragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class RunFragment extends Fragment implements OnMapReadyCallback, LocationUtils.MyLocation {

    LocationUtils locationUtils;
    Marker marker;
    GoogleMap gmap;

    private GoogleMap mMap;

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
        mapFragment.getMapAsync(this);
       /* initializeMap();*/
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
    }

    @Override
    public void locationUpdates(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        if(marker==null){
           // marker = this.gmap.addMarker(new MarkerOptions().position(latLng).title("marker"));
            CameraPosition googlePlex = CameraPosition.builder()
                    .target(latLng)
                    .zoom(18)
                    .bearing(0)
                    .tilt(45)
                    .build();
            this.gmap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));
        }
        else
        {
            marker.setPosition(latLng);
        }
    }
}
