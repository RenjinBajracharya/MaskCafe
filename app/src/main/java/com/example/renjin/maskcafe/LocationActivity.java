package com.example.renjin.maskcafe;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Mask Cafe, and move the camera.
        LatLng maskcafe = new LatLng(27.716371, 85.310742);
        mMap.addMarker(new MarkerOptions().position(maskcafe).title("Mask Cafe"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(maskcafe));
        //zoom camera and animation
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(maskcafe,18),3000,null);
    }
}
