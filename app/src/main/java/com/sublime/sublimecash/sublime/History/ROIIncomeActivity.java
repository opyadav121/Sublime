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

public class ROIIncomeActivity extends AppCompatActivity {
    WebView webRoi;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roiincome);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" ROI Income ");
        actionBar.show();
        myProfile = Session.GetProfile(getApplicationContext());

        webRoi = findViewById(R.id.webRoi);
        webRoi.loadUrl("http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Payment/roi/"+myProfile.UserLogin);
        webRoi.getSettings().setJavaScriptEnabled(true);
        webRoi.getSettings().setDomStorageEnabled(true);
        webRoi.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

    }
}
