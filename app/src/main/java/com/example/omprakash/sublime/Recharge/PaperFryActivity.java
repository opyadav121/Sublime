package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class PaperFryActivity extends AppCompatActivity {
    WebView webPaperfry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_fry);

        webPaperfry = findViewById(R.id.webPaperfry);
        webPaperfry.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=52&aff_id=7820");
    }
}
