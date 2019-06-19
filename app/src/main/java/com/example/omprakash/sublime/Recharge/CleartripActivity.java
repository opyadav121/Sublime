package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class CleartripActivity extends AppCompatActivity {

    WebView cleartrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleartrip);

        cleartrip = findViewById(R.id.cleartrip);
        cleartrip.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2175&aff_id=7820");
    }
}
