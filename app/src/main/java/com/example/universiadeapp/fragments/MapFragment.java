package com.example.universiadeapp.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.universiadeapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;


public class MapFragment extends Fragment {

    GoogleMap mGoogleMap;
    MapView mapView;
    View rootView;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
//                mGoogleMap.setMyLocationEnabled(true);

                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.008805, 92.724901)).title("Кластер «Радуга»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.050693, 92.894208)).title("Комплекс «Арена. Север»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.018195, 92.979318)).title("Стадион «Енисей»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(55.994552, 92.732085)).title("Комплекс «Академия биатлона»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.020361, 92.977166)).title("Крытый каток «Первомайский»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.022638, 92.789104)).title("Ледовый дворец «Рассвет»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.036458, 92.926079)).title("Ледовый дворец «Кристалл арена»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(55.982881, 92.807707)).title("Ледовая арена «Платинум Арена Красноярск»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(56.001898, 92.750197)).title("Кластер «Сопка»"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(55.998255, 92.884564)).title("Центральный стадион им. Ленинского комсомола"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(55.995540, 92.875064)).title("Дворец спорта им. И. Ярыгина"));
                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(55.961336, 92.795270)).title("Фанпарк «Бобровый лог»"));

                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(56.000334 , 92.866817)).zoom(10).build();
                mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
