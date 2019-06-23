package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.omprakash.sublime.R;

public class MakemyTripActivity extends AppCompatActivity {

    ProgressBar progressBar;
    WebView webMakeMyTrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makemy_trip);

        webMakeMyTrip = findViewById(R.id.webMakeMyTrip);
        webMakeMyTrip.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=44&aff_id=7820");
        webMakeMyTrip.getSettings().setJavaScriptEnabled(true);
        webMakeMyTrip.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webMakeMyTrip.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
