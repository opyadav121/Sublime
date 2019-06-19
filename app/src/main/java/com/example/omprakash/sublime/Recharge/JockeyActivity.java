package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class JockeyActivity extends AppCompatActivity {

    WebView webJockey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jockey);

        webJockey = findViewById(R.id.webJockey);
        webJockey.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2253&aff_id=7820");
    }
}
