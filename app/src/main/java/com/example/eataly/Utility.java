package com.example.eataly;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;

public class Utility {

    public static boolean verifyEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean verifyPassword(String pswd){
        if(pswd.length()>=6){
            return true;
        }
        return false;
    }

    public static boolean verifyPhone(String phone){
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean isLogged(Context context){
        String pref = Preferences.getSavedStringByKey(context, "TOKEN");
        if(pref.equals(""))
            return false;
        else
            return true;

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
