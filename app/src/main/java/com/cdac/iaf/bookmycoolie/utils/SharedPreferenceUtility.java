package com.cdac.iaf.bookmycoolie.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtility {

    SharedPreferences sharedPreferences;

    public SharedPreferenceUtility(Context context) {
    }

    public void saveToken(String token, Context context) {
        //System.out.println("token: "+token);
        sharedPreferences = context.getSharedPreferences("tokenPref",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("auth_token", token).apply();
    }

    public String getToken(Context context) {
        sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("auth_token", null);
    }

}
