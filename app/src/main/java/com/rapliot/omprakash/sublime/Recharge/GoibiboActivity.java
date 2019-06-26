package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rapliot.omprakash.sublime.R;

public class GoibiboActivity extends AppCompatActivity {
    WebView webGoibibo;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goibibo);

        webGoibibo = findViewById(R.id.webGoibibo);
        webGoibibo.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=442&aff_id=7820");
        webGoibibo.getSettings().setJavaScriptEnabled(true);
        webGoibibo.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webGoibibo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
