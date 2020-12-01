package com.example.smartness.Model;

import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

public class Services {
    private static DatabaseHandler _database;
    private static UserLocation _location;
    private static GoogleMap _googleMap;

    public static void setLocation(UserLocation location) {
        _location = location;
    }

    public static void setMap(GoogleMap map) {
        _googleMap = map;

    }

    public static void init() {
        _database = new DatabaseHandler();
    }

    public static DatabaseHandler getDatabase() {
        return _database;
    }

    public static UserLocation getUserLocation() {
        return _location;
    }

    public static GoogleMap getGoogleMap() { return _googleMap; }
}
