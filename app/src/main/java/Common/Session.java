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
            editor.putString("email", UserProfile.UserLogin);
            editor.putString("email", UserProfile.UserName);
            editor.putString("mobile", UserProfile.MobileNumber);
            editor.putString("address", UserProfile.Address);
            editor.putString("joining_amount", UserProfile.joiningAmount);
            editor.putString("activation_date", UserProfile.ActivationDate);
            editor.putString("sponsor", UserProfile.Sponsor);
            editor.putString("pan_no", UserProfile.PanNumber);
           // editor.putString("ProfileImage", UserProfile.ProfileImage);
           // editor.putString("UserID", UserProfile.UserID.toString());
          //  editor.putString("UserPassword", UserProfile.UserPassword);
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
            mProfile.UserLogin =  prefs.getString("email","");
            mProfile.UserName = prefs.getString("email","");
            mProfile.MobileNumber =  prefs.getString("mobile","");
            mProfile.Address = prefs.getString("address","");
            mProfile.PanNumber = prefs.getString("pan_no","");
            mProfile.Sponsor= prefs.getString("sponsor","");
            mProfile.ActivationDate= prefs.getString("activation_date","");
            mProfile.joiningAmount = prefs.getString("joining_amount","");
           // mProfile.ProfileImage = prefs.getString("ProfileImage","");
           // mProfile.UserID =  prefs.getString("UserID","");
          //  mProfile.UserPassword = prefs.getString("UserPassword","");

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
