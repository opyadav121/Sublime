package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class BigrockActivity extends AppCompatActivity {
    WebView webBigrock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigrock);

        webBigrock = findViewById(R.id.webrydon);
        webBigrock.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2223&aff_id=7820");
    }
}
