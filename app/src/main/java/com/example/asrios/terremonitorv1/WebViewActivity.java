package com.example.asrios.terremonitorv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String url = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/geojson.php";
        WebView webView = (WebView)this.findViewById(R.id.wV);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
