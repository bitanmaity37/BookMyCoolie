package com.cdac.iaf.bookmycoolie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.LoginResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SecuredSharedPreferenceUtils {

    private Context context;
    private SharedPreferences sharedPreferences;

    public SecuredSharedPreferenceUtils(Context context) throws GeneralSecurityException, IOException {
        this.context=context;
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences.create(context,context.getString(R.string.pref_file_key),
                masterKey,EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
    }
    public void saveLoginData(LoginResponse loginResponse){

        loginResponse.setJwtToken("Bearer "+loginResponse.getJwtToken());

        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(loginResponse);
        editor.putString("logindata",json);
        editor.apply();
    }

    public LoginResponse getLoginData(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("logindata","");
        LoginResponse lm = gson.fromJson(json,LoginResponse.class);
        return lm;
    }

    public void clearSharedPreference(){
        sharedPreferences.edit().clear().commit();
    }


}
