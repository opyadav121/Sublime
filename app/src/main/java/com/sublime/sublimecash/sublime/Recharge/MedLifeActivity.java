package com.sublime.sublimecash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sublime.sublimecash.sublime.R;

public class MedLifeActivity extends AppCompatActivity {
    WebView webMedLife;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_life);
        webMedLife = findViewById(R.id.webMedLife);
        webMedLife.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=3012&aff_id=7820");
        webMedLife.getSettings().setJavaScriptEnabled(true);
        webMedLife.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webMedLife.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}