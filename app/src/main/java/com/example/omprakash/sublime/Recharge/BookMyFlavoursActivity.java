package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class BookMyFlavoursActivity extends AppCompatActivity {
    WebView webBookMyFlaours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_my_flavours);
        webBookMyFlaours = findViewById(R.id.webBookMyFlaours);
        webBookMyFlaours.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=398&aff_id=7820");
    }
}
