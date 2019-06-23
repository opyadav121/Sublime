package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class Gud2Activity extends AppCompatActivity {
    WebView web2Gud;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gud2);

        web2Gud = findViewById(R.id.web2Gud);
        web2Gud.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=8885&aff_id=7820");
        web2Gud.getSettings().setJavaScriptEnabled(true);
        web2Gud.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        web2Gud.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
