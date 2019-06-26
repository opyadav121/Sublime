package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rapliot.omprakash.sublime.R;

public class JockeyActivity extends AppCompatActivity {

    WebView webJockey;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jockey);

        webJockey = findViewById(R.id.webJockey);
        webJockey.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2253&aff_id=7820");
        webJockey.getSettings().setJavaScriptEnabled(true);
        webJockey.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webJockey.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}