package com.rapliot.omprakash.sublime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class AddsActivity extends AppCompatActivity {

    WebView webAdds;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adds);
        webAdds = findViewById(R.id.webOyo);
        webAdds.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=9296&aff_id=7820&file_id=185040");
        webAdds.getSettings().setJavaScriptEnabled(true);
        webAdds.getSettings().setDomStorageEnabled(true);
        progressBar= findViewById(R.id.progressBar);
        webAdds.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
