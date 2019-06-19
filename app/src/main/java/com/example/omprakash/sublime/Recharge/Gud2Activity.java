package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class Gud2Activity extends AppCompatActivity {
    WebView web2Gud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gud2);

        web2Gud = findViewById(R.id.web2Gud);
        web2Gud.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=8885&aff_id=7820");
    }
}
