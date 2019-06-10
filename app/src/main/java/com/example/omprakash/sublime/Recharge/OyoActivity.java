package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class OyoActivity extends AppCompatActivity {

    WebView webOyo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyo);

        webOyo = findViewById(R.id.webOyo);
        webOyo.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=1362&aff_id=7820");
    }
}
