package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rapliot.omprakash.sublime.R;

public class FabHotelsActivity extends AppCompatActivity {

    WebView webFabHotels;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_hotels);

        webFabHotels = findViewById(R.id.webFabHotels);
        webFabHotels.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2417&aff_id=7820");
        webFabHotels.getSettings().setJavaScriptEnabled(true);
        webFabHotels.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webFabHotels.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
