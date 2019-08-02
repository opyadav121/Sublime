package utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ReferralTracker extends BroadcastReceiver {
    public String referrer = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    referrer = extras.getString("referrer");

                    if(referrer!=null&&(!referrer.toLowerCase().contains("google")||!referrer.toLowerCase().contains("utm_source=")) ){
                        PrefUtils.saveToPrefs(context, PrefUtils.referral_code, referrer);
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
