package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class EthicActivity extends AppCompatActivity {

    WebView webEthic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethic);

        webEthic = findViewById(R.id.webEthic);
        webEthic.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=3246&aff_id=7820");
    }
}
