package com.sublime.sublimecash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sublime.sublimecash.sublime.R;

public class BookMyFlavoursActivity extends AppCompatActivity {
    WebView webBookMyFlaours;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_my_flavours);
        webBookMyFlaours = findViewById(R.id.webBookMyFlaours);
        webBookMyFlaours.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=398&aff_id=7820");
        webBookMyFlaours.getSettings().setJavaScriptEnabled(true);
        webBookMyFlaours.getSettings().setDomStorageEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        webBookMyFlaours.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                progressBar.setVisibility(View.GONE);
                return false;
            }
        });
    }
}
