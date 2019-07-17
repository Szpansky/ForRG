package com.apps.mkacik.forRG.LaunchPadMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.apps.mkacik.forRG.App.App;
import com.apps.mkacik.forRG.R;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchPadMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String NAME = LaunchPadMapFragment.class.getName();
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 44;

    private double latitude, longitude;
    private String name;
    private GoogleMap map;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.currentPositionImage)
    ImageView currentPositionImage;

    @OnClick(R.id.currentPositionImage)
    public void onCurrentPositionClick() {
        checkPermissionsAndSetLocation();
    }


    public static LaunchPadMapFragment newInstance(String name, double latitude, double longitude) {
        Bundle args = new Bundle();
        args.putDouble(LATITUDE, latitude);
        args.putDouble(LONGITUDE, longitude);
        args.putString(NAME, name);

        LaunchPadMapFragment fragment = new LaunchPadMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launchpad_map, container, false);
        ButterKnife.bind(this, view);

        assert getArguments() != null;
        latitude = getArguments().getDouble(LATITUDE);
        longitude = getArguments().getDouble(LONGITUDE);
        name = getArguments().getString(NAME);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.getUiSettings().setMyLocationButtonEnabled(false);
        LatLng location = new LatLng(longitude, latitude);
        map.addMarker(new MarkerOptions().position(location).title(name));

        map.moveCamera(CameraUpdateFactory.newLatLng(location));

        map.setMinZoomPreference(10);
        map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                map.resetMinMaxZoomPreference();
            }
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
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

    private void checkPermissionsAndSetLocation() {
        if (ActivityCompat.checkSelfPermission(App.getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            assert getActivity() != null : "error with activity in map fragment";
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            // Permission has already been granted
            setFusedLocationProviderClient();
        }
    }


    @SuppressLint("MissingPermission")
    private void setFusedLocationProviderClient() {
        assert getActivity() != null : "error with activity in map fragment";
        LocationServices.getFusedLocationProviderClient(App.getContext()).getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    map.setMinZoomPreference(10);
                    map.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                        @Override
                        public void onCameraMove() {
                            map.resetMinMaxZoomPreference();
                        }
                    });
                    map.getUiSettings().setMyLocationButtonEnabled(true);
                    map.setMyLocationEnabled(true);
                    currentPositionImage.setVisibility(View.GONE);
                } else {
                    map.setMyLocationEnabled(false);
                    map.getUiSettings().setMyLocationButtonEnabled(false);
                    currentPositionImage.setVisibility(View.VISIBLE);

                    Toast.makeText(getContext(), R.string.location_device_denied, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setFusedLocationProviderClient();
                } else {
                    Toast.makeText(getActivity(), R.string.permission_error_info, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}