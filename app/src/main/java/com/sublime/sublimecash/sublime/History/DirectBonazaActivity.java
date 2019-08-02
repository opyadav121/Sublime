package com.sublime.sublimecash.sublime.History;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sublime.sublimecash.sublime.R;

import Common.Session;
import Model.Profile;

public class DirectBonazaActivity extends AppCompatActivity {
    WebView webBonaza;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_bonaza);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Direct Bonaza ");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());

        webBonaza = findViewById(R.id.webBonaza);
        webBonaza.loadUrl("http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Payment/direct_bonanza/"+myProfile.UserLogin);
        webBonaza.getSettings().setJavaScriptEnabled(true);
        webBonaza.getSettings().setDomStorageEnabled(true);
        webBonaza.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }
}