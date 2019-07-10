package com.sublime.sublimecash.sublime;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import Common.Session;
import Model.Profile;

public class RefferActivity extends AppCompatActivity {
    TextView txtReferalCode;
    LinearLayout btnRefer;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reffer);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Refer & Earn");
        actionBar.show();

        txtReferalCode = findViewById(R.id.txtReferalCode);
        btnRefer = findViewById(R.id.btnRefer);
        btnRefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String InviteMessage = "Click here to Install application\n  https://play.google.com/store/apps/details?id=com.sublime.sublimecash.sublime&hl=en"+"\nUse the Referral Code: "+myProfile.UserID;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,InviteMessage);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"SEND"));
            }
        });
        myProfile = Session.GetProfile(getApplicationContext());
        txtReferalCode.setText(myProfile.UserID);
    }

}
