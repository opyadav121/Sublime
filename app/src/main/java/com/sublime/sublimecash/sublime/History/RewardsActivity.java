package com.sublime.sublimecash.sublime.History;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.sublime.sublimecash.sublime.R;

import Common.Session;
import Model.Profile;

public class RewardsActivity extends AppCompatActivity {
    WebView webReward;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(" Rewards ");
        actionBar.show();

        myProfile = Session.GetProfile(getApplicationContext());

        webReward = findViewById(R.id.webReward);
        webReward.loadUrl("http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/user/rewards/"+myProfile.UserLogin);
        webReward.getSettings().setJavaScriptEnabled(true);
        webReward.getSettings().setDomStorageEnabled(true);
        webReward.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }
}
