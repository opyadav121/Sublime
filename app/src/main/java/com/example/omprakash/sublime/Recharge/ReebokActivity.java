package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class ReebokActivity extends AppCompatActivity {

    WebView webReebok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reebok);
        webReebok = findViewById(R.id.webReebok);
        webReebok.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=9221&aff_id=7820");
    }
}
