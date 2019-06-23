package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class OyoActivity extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webOyo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyo);
        webOyo = findViewById(R.id.webOyo);
        webOyo.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=1362&aff_id=7820");
        webOyo.getSettings().setJavaScriptEnabled(true);
        webOyo.getSettings().setDomStorageEnabled(true);
        progressBar= findViewById(R.id.progressBar);
        webOyo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
