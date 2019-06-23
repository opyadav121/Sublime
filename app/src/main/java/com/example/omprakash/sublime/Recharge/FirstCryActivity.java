package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class FirstCryActivity extends AppCompatActivity {

    WebView webfirstCry;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_cry);
        webfirstCry = findViewById(R.id.webfirstCry);
        webfirstCry.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2031&aff_id=7820");
        webfirstCry.getSettings().setJavaScriptEnabled(true);
        webfirstCry.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webfirstCry.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
