package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rapliot.omprakash.sublime.R;

public class SukhhiActivity extends AppCompatActivity {
    WebView webSukhhi;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sukhhi);
        webSukhhi = findViewById(R.id.webSukhhi);
        webSukhhi.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=8273&aff_id=7820");
        webSukhhi.getSettings().setJavaScriptEnabled(true);
        webSukhhi.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webSukhhi.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
