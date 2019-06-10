package Common;

import android.content.Context;
import android.content.SharedPreferences;

import Model.Profile;

public class Session {

    private static final String SESSION_NAME = "Sublime";


    public static boolean AddProfile(Context context, Profile UserProfile)
    {
        try {
            SharedPreferences prefs = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("UserLogin", UserProfile.UserLogin);
            editor.putString("UserName", UserProfile.UserName);
            editor.putString("MobileNumber", UserProfile.MobileNumber);
            editor.putString("ProfileImage", UserProfile.ProfileImage);
            editor.putString("UserID", UserProfile.UserID.toString());
            editor.putString("UserPassword", UserProfile.UserPassword);
            editor.commit();
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public static Profile GetProfile(Context context)
    {
        Profile mProfile = new Profile();
        try {
            SharedPreferences prefs = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
            mProfile.UserLogin =  prefs.getString("UserLogin","");
            mProfile.UserName = prefs.getString("UserName","");
            mProfile.MobileNumber =  prefs.getString("MobileNumber","");
            mProfile.ProfileImage = prefs.getString("ProfileImage","");
            mProfile.UserID =  prefs.getString("UserID","");
            mProfile.UserPassword = prefs.getString("UserPassword","");

            return mProfile;
        }
        catch (Exception ex)
        {
            return null;
        }
    }


    public static void LogOff(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
