package com.example.eataly;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;

public class Preferences{
    private static final String SharedPrefs="com.example.eataly.saved_preferences";

    public static String getSavedStringByKey(Context context,String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }

    public static void saveStringPreferences(Context context, String key, String pref){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,pref);
        editor.apply();
    }

    public static boolean getSavedBooleanByKey(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefs, MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void saveBooleanPreferences(Context context, String key, boolean bool){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefs,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, bool);
        editor.apply();
    }
}
