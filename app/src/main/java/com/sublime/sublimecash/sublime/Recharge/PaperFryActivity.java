package com.sublime.sublimecash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sublime.sublimecash.sublime.R;

public class PaperFryActivity extends AppCompatActivity {
    WebView webPaperfry;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_fry);

        webPaperfry = findViewById(R.id.webPaperfry);
        webPaperfry.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=52&aff_id=7820");
        webPaperfry.getSettings().setJavaScriptEnabled(true);
        webPaperfry.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webPaperfry.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
