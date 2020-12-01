package com.example.smartness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.smartness.Model.DatabaseHandler;
import com.example.smartness.Model.Services;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.MapMaker;

public class Add extends AppCompatActivity {

    EditText desc;
    EditText name;
    ImageButton upload;
    ImageView photo;
    Intent returnIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        desc = (EditText)findViewById(R.id.ExplainEd);
        name = (EditText)findViewById(R.id.editText);

        upload = (ImageButton) findViewById(R.id.imageView3);
        photo = (ImageView)findViewById(R.id.photo);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Add.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(Add.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
            }
        });

    }

    public void submit(View view) {
        /* IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Services.getUserLocation().setName(name.getText().toString());
        Services.getUserLocation().setDesc(desc.getText().toString());
        Services.getDatabase().uploadLocation(Services.getUserLocation());
        Intent intent = new Intent(this,MapScreen.class);
        startActivity(new Intent(this, MapScreen.class));
        */
        Services.getUserLocation().setName((name.getText().toString()));
        Services.getUserLocation().setDesc((desc.getText().toString()));
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

//    public void addTheMarker() {
//        Marker marker = Services.getGoogleMap()
//                .addMarker(new MarkerOptions()
//                        .position(new LatLng(Services.getUserLocation().getLatitude()
//                                ,Services.getUserLocation().getLongitude()))
//                        .title(name.getText().toString())
//                        .snippet("Name: " + name.getText().toString())
//                        .snippet("Description: " + desc.getText().toString())
//                );
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        photo.setImageBitmap(bitmap);
    }

    public void bckToMap(View view) {
        startActivity(new Intent(this, MapScreen.class));
    }
}
