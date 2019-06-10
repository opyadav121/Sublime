package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class NNNOActivity extends AppCompatActivity {

    WebView webNNNO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nnno);

        webNNNO = findViewById(R.id.webNNNO);
        webNNNO.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=2860&aff_id=7820");
    }
}
