package com.sublime.sublimecash.sublime;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import Common.Session;
import Model.Profile;
public class MainActivity extends AppCompatActivity  {
    Profile myProfile;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        String currentVersion = "";
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String finalCurrentVersion = currentVersion;
        new CheckIsUpdateReady("https://play.google.com/store/apps/details?id=" + getPackageName() + "&hl=en", new UrlResponce() {
            @Override
            public void onReceived(String resposeStr) {
                if (!resposeStr.equalsIgnoreCase(finalCurrentVersion)){
                    AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("New Version Available" );
                    builder.setMessage("Your version of this app is need to Update, Please go to Play Store and update");
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
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
                    });
                    builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.sublime.sublimecash.sublime&hl=en")));
                            MainActivity.this.finish();
                        }
                    });
                    AlertDialog Alert = builder.create();
                    Alert.show();
                }else{
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
              //  Toast.makeText(getBaseContext(),resposeStr,Toast.LENGTH_SHORT).show();
            }
        }).execute();

      //  ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

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


    public class CheckIsUpdateReady extends AsyncTask<Void, String, String> {
        String appURL="";
        private UrlResponce mUrlResponce;
        public CheckIsUpdateReady(String appURL, UrlResponce callback) {
            this.appURL=appURL;
            mUrlResponce = callback;
        }

        @Override

        protected String doInBackground(Void... voids) {

            String newVersion = null;

            try {
                Document document = Jsoup.connect(appURL)
                        .timeout(20000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get();
                if (document != null) {
                    Elements element = document.getElementsContainingOwnText("Current Version");
                    for (Element ele : element) {
                        if (ele.siblingElements() != null) {
                            Elements sibElemets = ele.siblingElements();
                            for (Element sibElemet : sibElemets) {
                                newVersion = sibElemet.text();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }

        @Override
        protected void onPostExecute(String onlineVersion) {
            super.onPostExecute(onlineVersion);
            if (onlineVersion != null && !onlineVersion.isEmpty()) {
                mUrlResponce.onReceived(onlineVersion);
            }

          //  Log.d("update", " playstore App version " + onlineVersion);

        }
    }
    public abstract class UrlResponce {
        public abstract void onReceived(String resposeStr);

    }

}
