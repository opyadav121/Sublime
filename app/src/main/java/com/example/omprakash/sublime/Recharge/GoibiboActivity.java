package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class GoibiboActivity extends AppCompatActivity {
    WebView webGoibibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goibibo);

        webGoibibo = findViewById(R.id.webGoibibo);
        webGoibibo.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=442&aff_id=7820");
    }

}
