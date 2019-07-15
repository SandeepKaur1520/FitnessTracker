package com.fitness.tracker.gymfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.fitness.tracker.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RunFragment extends Fragment implements OnMapReadyCallback {

        private GoogleMap mMap;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.run_fragment);
            /*MapFragment mapFragment = (MapFragment) getFragmentManager() .findFragmentById(R.id.gym_exercise_id);
            mapFragment.getMapAsync(this);*/
        }

    private void setContentView(int run_fragment) {
    }

     /* @Override
      public void onMapReady(GoogleMap googleMap) {


          // Add a marker in Sydney, Australia, and move the camera.
          LatLng sydney = new LatLng(-34, 151);
          mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
          mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
      }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }
}
