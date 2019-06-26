package com.rapliot.omprakash.sublime;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import Common.Session;
import Model.Profile;

public class MainActivity extends AppCompatActivity {
    ImageView splash;
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                CheckLogin();
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
}