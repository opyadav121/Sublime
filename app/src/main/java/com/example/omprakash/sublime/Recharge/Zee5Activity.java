package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class Zee5Activity extends AppCompatActivity {

    WebView webZee5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zee5);
        webZee5 = findViewById(R.id.webZee5);
        webZee5.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=9296&aff_id=7820a");
    }
}
