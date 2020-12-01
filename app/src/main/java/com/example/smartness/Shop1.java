package com.example.smartness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Shop1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop1);
    }

    public void nextPage(View view) {
        startActivity(new Intent(this, Page2.class));
    }

    public void bkHome(View view) {
        startActivity(new Intent(this, HomeScreen.class));
    }
}
