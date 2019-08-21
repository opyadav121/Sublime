package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefUtils {
    public static String referral_code = "reffercode";

    public static void saveToPrefs(Context context, String key, String value) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putString(key, value);
        edit.apply();

    }
    /**
     *
     * @param context
     * @param key
     * @return value of key
     */
    public static String getFromPrefs(Context context, String key) {
        if (context != null) {
            SharedPreferences sharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(context);
            try {
                return sharedPrefs.getString(key, "");
            } catch (Exception e) {
                return "";
            }
        } else {
            return "";
        }
    }
}
