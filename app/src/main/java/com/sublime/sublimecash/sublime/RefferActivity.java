package com.sublime.sublimecash.sublime;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import Common.Session;
import Model.Profile;

public class RefferActivity extends AppCompatActivity {
    TextView txtReferalCode;
    LinearLayout btnRefer;
    Profile myProfile;
    public String TAG ;
    private static final int REQUEST_INVITE = 0;
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
                    share();
                }
            });
            myProfile = Session.GetProfile(getApplicationContext());
            txtReferalCode.setText(myProfile.UserID);
    }
    public void share(){
        String InviteMessage = "Inviting you to join SublimeCash\n" + "https://play.google.com/store/apps/details?id=com.sublime.sublimecash.sublime&referrer="+myProfile.UserID;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,InviteMessage);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent,"SEND"));
    }


  /*  public void setGenerateLink(){
        Log.e("main", "create link ");
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://sublimecash.com/"))
                .setDynamicLinkDomain("https://sublimecash.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                //.setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .buildDynamicLink();
//click -- link -- google play store -- inistalled/ or not  ----
        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e("main", "  Long refer "+ dynamicLink.getUri());
        //   https://referearnpro.page.link?apn=blueappsoftware.referearnpro&link=https%3A%2F%2Fwww.blueappsoftware.com%2F
        // apn  ibi link
        // manuall link
        String sharelinktext  = "https://sublimecash.page.link/?"+
                "link=https://www.sublimecash.com/"+
                "&apn="+ "com.sublime.sublimecash.sublime"+
                "&st="+"Hi \n"+
                "&sd="+"Inviting you to join SublimeCash\n" + "Use my referrer code :\n" + "\n" +myProfile.UserID;
        // shorten the link
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                //.setLongLink(dynamicLink.getUri())
                .setLongLink(Uri.parse(sharelinktext))  // manually
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            Log.e("main ", "short link "+ shortLink.toString());
                            // share app dialog
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,  shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);
                        } else {
                            Log.e("main", " error "+task.getException() );
                        }
                    }
                });
    }   */
}
