package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class RydonActivity extends AppCompatActivity {
    WebView webrydon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rydon);

        webrydon = findViewById(R.id.webrydon);
        webrydon.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=7883&aff_id=7820");
    }
}
