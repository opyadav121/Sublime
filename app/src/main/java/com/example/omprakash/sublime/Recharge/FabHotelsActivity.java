package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class FabHotelsActivity extends AppCompatActivity {
    WebView webFabHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_hotels);

        webFabHotels = findViewById(R.id.webFabHotels);
        webFabHotels.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2417&aff_id=7820");
    }
}
