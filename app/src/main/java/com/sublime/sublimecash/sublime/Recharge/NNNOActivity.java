package com.sublime.sublimecash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sublime.sublimecash.sublime.R;

public class NNNOActivity extends AppCompatActivity {

    WebView webNNNO;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nnno);

        webNNNO = findViewById(R.id.webNNNO);
        webNNNO.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2860&aff_id=7820");
        webNNNO.getSettings().setJavaScriptEnabled(true);
        webNNNO.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webNNNO.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
