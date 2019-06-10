package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class NetMaedsActivity extends AppCompatActivity {

    WebView webnetmeds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_maeds);

        webnetmeds= findViewById(R.id.webnetmeds);
        webnetmeds.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2119&aff_id=7820");
    }
}
