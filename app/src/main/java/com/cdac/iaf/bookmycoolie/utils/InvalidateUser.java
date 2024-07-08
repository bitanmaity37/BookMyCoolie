package com.cdac.iaf.bookmycoolie.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.cdac.iaf.bookmycoolie.activities.ADMIN.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.activities.AdminLoginActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class InvalidateUser {



    public static void invalidate(Context context) throws GeneralSecurityException, IOException {


        SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
        securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(context);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context);
        materialAlertDialogBuilder.setCancelable(false)
                .setTitle("LOGGING OUT")
                .setMessage("PLEASE LOGIN AGAIN")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        securedSharedPreferenceUtils.clearSharedPreference();
                        context.startActivity(new Intent(context, AdminLoginActivity.class));

                        dialogInterface.dismiss();
                        ((Activity) context).finishAffinity();


                    }
                }).show();
    }
}
