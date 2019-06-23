package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class BigrockActivity extends AppCompatActivity {
    WebView webBigrock;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigrock);

        webBigrock = findViewById(R.id.webBigrock);
        webBigrock.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2223&aff_id=7820");
        webBigrock.getSettings().setJavaScriptEnabled(true);
        webBigrock.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webBigrock.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
