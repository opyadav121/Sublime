package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class ReebokActivity extends AppCompatActivity {

    WebView webReebok;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reebok);
        webReebok = findViewById(R.id.webReebok);
        webReebok.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=9221&aff_id=7820");
        webReebok.getSettings().setJavaScriptEnabled(true);
        webReebok.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webReebok.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
