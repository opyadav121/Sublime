package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class GiftAloveActivity extends AppCompatActivity {
    WebView webGiftAlove;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_alove);

        webGiftAlove = findViewById(R.id.webGiftAlove);
        webGiftAlove.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=1292&aff_id=7820");
        webGiftAlove.getSettings().setJavaScriptEnabled(true);
        webGiftAlove.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webGiftAlove.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
