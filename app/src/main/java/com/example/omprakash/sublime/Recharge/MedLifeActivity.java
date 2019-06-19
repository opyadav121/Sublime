package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class MedLifeActivity extends AppCompatActivity {
    WebView webMedLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_life);
        webMedLife = findViewById(R.id.webMedLife);
        webMedLife.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=3012&aff_id=7820");
    }
}
