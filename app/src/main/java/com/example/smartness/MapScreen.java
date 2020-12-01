package com.example.smartness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartness.Model.DatabaseHandler;
import com.example.smartness.Model.Services;
import com.example.smartness.Model.User;
import com.example.smartness.Model.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

public class MapScreen extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private boolean locationPermissionGranted = false;
    private GoogleMap mMap;
    private LatLng latLng;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final String TAG = "MapScreen";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 17f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        ImageButton HomeButton = (ImageButton) findViewById(R.id.plus);
        ImageView Back = (ImageView) findViewById(R.id.imageView2);
        HomeButton.bringToFront();
        Back.bringToFront();

        getLocationPermission();
        if (!isLocationEnabled()) {
            Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else {

        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation:  getting the current decice location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (locationPermissionGranted) {
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Log.d(TAG, ((Location)task.getResult()).toString());
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                            UserLocation userLocation = new UserLocation(currentLocation.getLongitude(), currentLocation.getLatitude());
                            Services.setLocation(userLocation);
                            Log.d("blabla", userLocation.toString());
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapScreen.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG,"map is ready");
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
        mMap = googleMap;
        Services.setMap(mMap);
        mMap.addMarker(new MarkerOptions()
        .position(new LatLng(31.932617, 34.809564))
        .title("Broken lamp")
        .snippet("There is a broken lamp on the street"));

        mMap.addMarker(new MarkerOptions()
        .position(new LatLng(31.932908, 34.793074))
        .title("Broken lamp")
        .snippet("There is a broken lamp on the street"));

        if (locationPermissionGranted) {
            getDeviceLocation();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        Log.d(TAG, "moveCamera: moving camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapScreen.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permission");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        ActivityCompat.requestPermissions(this,
                permissions,
                LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        locationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            locationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    locationPermissionGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    public void onLoctionChange(Location location) {
        String msg = "Updated location: " + Double.toString(location.getLatitude()) + ", " +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void back(View view) {
        startActivity(new Intent(this, HomeScreen.class));
    }

    public void addClick(View view) {
      startActivityForResult(new Intent(this, Add.class), 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LatLng pos = new LatLng(Services.getUserLocation().getLatitude(), Services.getUserLocation().getLongitude());
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onRequestPermissionsResult: called.");
                // get the list of strings here
                // do operations on the list
                if (Services.getUserLocation() != null) {
                    MarkerOptions markerOptions = addTheMarker();
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Services.getUserLocation().getLatitude()
                            , Services.getUserLocation().getLongitude())));
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
    @Override
    public boolean onMarkerClick(final Marker marker) {

        if (marker.equals())
        {
            //handle click here
        }
    }*/

    public MarkerOptions addTheMarker() {
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.setOnMarkerClickListener(this);
        markerOptions.position(new LatLng(Services.getUserLocation().getLatitude()
                ,Services.getUserLocation().getLongitude()));
        if(Services.getUserLocation().getName() != null && Services.getUserLocation().getDesc() != null) {
            markerOptions.title(Services.getUserLocation().getName());
            markerOptions.snippet("Description: " + Services.getUserLocation().getDesc());
        } else {
            Toast.makeText(this,"ENTER TEXT!", Toast.LENGTH_SHORT).show();
        }
        return markerOptions;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
