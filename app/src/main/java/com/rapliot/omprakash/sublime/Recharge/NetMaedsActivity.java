package com.rapliot.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rapliot.omprakash.sublime.R;

public class NetMaedsActivity extends AppCompatActivity {

    WebView webnetmeds;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_maeds);

        webnetmeds= findViewById(R.id.webnetmeds);
        webnetmeds.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2119&aff_id=7820");
        webnetmeds.getSettings().setJavaScriptEnabled(true);
        webnetmeds.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webnetmeds.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
