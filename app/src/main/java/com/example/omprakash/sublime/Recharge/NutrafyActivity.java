package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class NutrafyActivity extends AppCompatActivity {

    WebView webNutrafy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrafy);

        webNutrafy = findViewById(R.id.webNutrafy);
        webNutrafy.loadUrl("http://tracking.vcommission.com/aff_c?offer_id=7535&aff_id=7820");
    }
}
