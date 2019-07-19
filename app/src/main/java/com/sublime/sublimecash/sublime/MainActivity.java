package com.sublime.sublimecash.sublime;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.appvirality.android.AppviralityAPI;

import Common.Session;
import Model.PrefManager;
import Model.Profile;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 123;
    ImageView splash;
    Profile myProfile;
    private PrefManager prefManager;
    Context context;
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
    public void Register(){
        AppviralityAPI.init(getApplicationContext());
        prefManager.setFirstTimeLaunch(false);

        //startActivity(new Intent(MainActivity.this, RegisterActivity.class));
        AppviralityAPI.getInstance(getApplicationContext(), new AppviralityAPI.UserInstance() {
            @Override
            public void onInstance(AppviralityAPI instance) {
                String userkey = instance.getUserKey();
                boolean hasReferrer = instance.hasReferrer();
                String referralCode = AppviralityAPI.getFriendReferralCode();
                // The status of isExistingUser will not be changed until user un-install and re-install the App again
                boolean isExistingUser = instance.isExistingUser();
                Log.i("AV isExisting User: ",""+ isExistingUser);
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                intent.putExtra("Reffral",referralCode);
                intent.putExtra("userkey",userkey);
                startActivity(intent);
            }
        });
        finish();
    }

  /* public void Update(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(context);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.

                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, this,

                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
               // log("Update flow failed! Result code: " + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }  */
}
