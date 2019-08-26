package com.sublime.sublimecash.sublime;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import Common.Session;
import Model.Profile;
public class MainActivity extends AppCompatActivity implements ForceUpdateChecker.OnUpdateNeededListener {
    Profile myProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckLogin();
               // Intent i = new Intent(MainActivity.this, HomeActivity.class);
               // startActivity(i);
                //finish();
            }
        },2000);
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
  @Override
    public void onUpdateNeeded(String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue reposting.")
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore(updateUrl);
                            }
                        }).setNegativeButton("No, thanks",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create();
        dialog.show();
    }
    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
