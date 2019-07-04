package com.sublime.sublimecash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sublime.sublimecash.sublime.R;

public class NutrafyActivity extends AppCompatActivity {

    WebView webNutrafy;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrafy);

        webNutrafy = findViewById(R.id.webNutrafy);
        webNutrafy.loadUrl("http://tracking.vcommission.com/aff_c?offer_id=7535&aff_id=7820");
        webNutrafy.getSettings().setJavaScriptEnabled(true);
        webNutrafy.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webNutrafy.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
