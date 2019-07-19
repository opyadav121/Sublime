package com.sublime.sublimecash.sublime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import Common.Session;
import Model.Profile;

public class TreeViewActivity extends AppCompatActivity {
    WebView webTree;
    RequestQueue requestQueue;
    Profile myProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_view);
        requestQueue = Volley.newRequestQueue(this);
        myProfile = Session.GetProfile(getApplicationContext());
        webTree = findViewById(R.id.webTree);
        webTree.loadUrl("http://202.66.174.167/plesk-site-preview/sublimecash.com/202.66.174.167/ws/users/index.php/Home/Tree2/"+myProfile.UserLogin);
        webTree.getSettings().setJavaScriptEnabled(true);
        webTree.getSettings().setDomStorageEnabled(true);
        webTree.getSettings().setSupportZoom(true);
        webTree.getSettings().setBuiltInZoomControls(true);
        webTree.getSettings().setDisplayZoomControls(true);
        webTree.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
        myProfile = Session.GetProfile(this);
    }
}
