package com.sublime.sublimecash.sublime;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import Common.Session;
import Model.PrefManager;
import Model.Profile;

public class MainActivity extends AppCompatActivity  {
    private static final int MY_REQUEST_CODE = 123;
    private static final String TAG = "test";
    ImageView splash;
    Profile myProfile;
    String referrer;
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
           // Update();
            Register();
            finish();
        }else {
            CheckLogin();
        }
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // Intent i = new Intent(MainActivity.this, HomeActivity.class);
               // startActivity(i);
                //finish();
            }
        },1000);

    }

    private void CheckLogin() {
        myProfile = Session.GetProfile(this);
        if (myProfile == null || myProfile.UserLogin.matches("")) {
            Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(LoginIntent);
            MainActivity.this.finish();
        } else {
            Intent expenseIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(expenseIntent);
            MainActivity.this.finish();
        }
    }
    public void Register() {
        prefManager.setFirstTimeLaunch(false);
         class InstallReferrerReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
             referrer = intent.getStringExtra("referrer");

            }
        }

        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
         intent.putExtra("Reffer", referrer);
        startActivity(intent);
    }
}
