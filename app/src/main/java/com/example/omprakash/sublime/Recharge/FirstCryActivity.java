package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class FirstCryActivity extends AppCompatActivity {

    WebView webfirstCry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_cry);
        webfirstCry = findViewById(R.id.webfirstCry);
        webfirstCry.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2031&aff_id=7820");
    }
}
