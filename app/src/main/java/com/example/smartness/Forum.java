package com.example.smartness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class Forum extends AppCompatActivity {

    ImageView back;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        back = (ImageView)findViewById(R.id.back) ;
        back.bringToFront();

        webView = (WebView)findViewById(R.id.webView2);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://smartness.discussion.community/categories");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

    public void backHome(View view) { startActivity(new Intent(this, HomeScreen.class));}
}
