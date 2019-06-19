package com.example.omprakash.sublime.Recharge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.omprakash.sublime.R;

public class PizzaHutActivity extends AppCompatActivity {

    WebView webPizza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_hutt);

        webPizza = findViewById(R.id.webPizza);
        webPizza.loadUrl("https://tracking.vcommission.com/aff_c?offer_id=442&aff_id=7820");
    }
}
