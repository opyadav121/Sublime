package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class GiftAloveActivity extends AppCompatActivity {
    WebView webGiftAlove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_alove);

        webGiftAlove = findViewById(R.id.webGiftAlove);
        webGiftAlove.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=1292&aff_id=7820");
    }
}