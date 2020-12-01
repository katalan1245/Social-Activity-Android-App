package com.example.smartness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.Map;

public class HomeScreen extends AppCompatActivity {


    private static final String TAG = "HomeScreen";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //ImageButton HomeButton = (ImageButton) findViewById(R.id.HomeButton);
        //HomeButton.bringToFront();

        //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
         //       .findFragmentById(R.id.map);
    }

    public boolean ServicesFine() {
        Log.d(TAG,"Services Fine: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(HomeScreen.this);

        if(available == ConnectionResult.SUCCESS) {
            //everything is ok
            Log.d(TAG, "ServicesFine: Google services working fine");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occurred but we can resolve it
            Log.d(TAG, "ServicesFine: an error occurred but we can resolve it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(HomeScreen.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void toMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void toMap(View view) {
        startActivity(new Intent(this, MapScreen.class));
    }

    public void toWeb(View view) {
        startActivity(new Intent(this, Website.class));
    }

    public void toForum(View view) { startActivity(new Intent(this, Forum.class));}

    public void toShop(View view) { startActivity(new Intent(this, Shop1.class));}
}
