package com.example.eataly;

import android.util.Patterns;

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
}
