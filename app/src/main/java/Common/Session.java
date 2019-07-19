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
            editor.putString("name", UserProfile.UserName);
            editor.putString("mobile", UserProfile.MobileNumber);
            editor.putString("address", UserProfile.Address);
            editor.putString("joining_amount", UserProfile.joiningAmount);
            editor.putString("activation_date", UserProfile.ActivationDate);
            editor.putString("sponsor", UserProfile.Sponsor);
            editor.putString("pan_no", UserProfile.PanNumber);
            editor.putString("user_id",UserProfile.UserID);
           // editor.putString("sponsor_id",UserProfile.SponserId);
            editor.putString("under_userid",UserProfile.UnderUserId);
            editor.putString("dob",UserProfile.Dob);
            editor.putString("original_email",UserProfile.original_email);
            editor.putString("father_name",UserProfile.FatherName);
            editor.putString("mother_name",UserProfile.MotherName);
            editor.putString("gender",UserProfile.gender);
            editor.putString("district", UserProfile.district);
            editor.putString("state",UserProfile.State);
            editor.putString("pincode",UserProfile.PinCode);
            editor.putString("passport",UserProfile.Passport);
            editor.putString("nominee",UserProfile.nominee);
            editor.putString("relation",UserProfile.nomineeRelation);
            editor.putString("marital",UserProfile.marital);
            editor.putString("commission_paid",UserProfile.commission_paid);
            editor.putString("total_left_user",UserProfile.total_left_user);
            editor.putString("total_right_user",UserProfile.total_right_user);
            editor.putString("left_carry",UserProfile.left_carry);
            editor.putString("right_carry", UserProfile.right_carry);
            editor.putString("left_business",UserProfile.left_business);
            editor.putString("right_business",UserProfile.right_business);
            editor.putString("occupation",UserProfile.occupation);
            editor.putString("joining_amount",UserProfile.joiningAmount);
            editor.putString("side",UserProfile.side);
            editor.putString("E-Wallet",UserProfile.EWallet);
            editor.putString("S-Wallet",UserProfile.SWallet);
            editor.putString("Pending_balance",UserProfile.PendingWallet);
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
            mProfile.UserName = prefs.getString("name","");
            mProfile.MobileNumber =  prefs.getString("mobile","");
            mProfile.Address = prefs.getString("address","");
            mProfile.PanNumber = prefs.getString("pan_no","");
            mProfile.Sponsor= prefs.getString("sponsor","");
            mProfile.ActivationDate= prefs.getString("activation_date","");
            mProfile.joiningAmount = prefs.getString("joining_amount","");
            mProfile.UserID = prefs.getString("user_id","");
            mProfile.original_email=prefs.getString("original_email","");
            mProfile.UnderUserId = prefs.getString("under_userid","");
            mProfile.Dob = prefs.getString("dob","");
            mProfile.FatherName=prefs.getString("father_name","");
            mProfile.MotherName= prefs.getString("mother_name","");
            mProfile.gender= prefs.getString("gender","");
            mProfile.district = prefs.getString("district","");
            mProfile.State = prefs.getString("state","");
            mProfile.PinCode=prefs.getString("pincode","");
            mProfile.Passport = prefs.getString("passport","");
            mProfile.nominee = prefs.getString("nominee","");
            mProfile.nomineeRelation=prefs.getString("relation","");
            mProfile.marital= prefs.getString("marital","");
            mProfile.commission_paid= prefs.getString("commission_paid","");
            mProfile.total_left_user=prefs.getString("total_left_user","");
            mProfile.total_right_user= prefs.getString("total_right_user","");
            mProfile.left_carry= prefs.getString("left_carry","");
            mProfile.right_carry = prefs.getString("right_carry","");
            mProfile.left_business = prefs.getString("left_business","");
            mProfile.right_business=prefs.getString("right_business","");
            mProfile.occupation = prefs.getString("occupation","");
            mProfile.joiningAmount = prefs.getString("joining_amount","");
            mProfile.side=prefs.getString("side","");
            mProfile.EWallet = prefs.getString("E-Wallet","");
            mProfile.SWallet = prefs.getString("S-Wallet","");
            mProfile.PendingWallet=prefs.getString("Pending_balance","");
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
