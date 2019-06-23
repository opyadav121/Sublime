package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class Zee5Activity extends AppCompatActivity {
    WebView webZee5;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zee5);
        webZee5 = findViewById(R.id.webZee5);
        webZee5.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=9296&aff_id=7820a");
        webZee5.getSettings().setJavaScriptEnabled(true);
        webZee5.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webZee5.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
