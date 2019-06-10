package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class MakemyTripActivity extends AppCompatActivity {

    WebView webMakeMyTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makemy_trip);

        webMakeMyTrip = findViewById(R.id.webMakeMyTrip);
        webMakeMyTrip.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=44&aff_id=7820");
    }
}
