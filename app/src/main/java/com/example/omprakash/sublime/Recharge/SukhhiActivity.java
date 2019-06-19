package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class SukhhiActivity extends AppCompatActivity {
    WebView webSukhhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukhhi);
        webSukhhi = findViewById(R.id.webSukhhi);
        webSukhhi.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=8273&aff_id=7820");
    }
}
