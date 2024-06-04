package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.activities.USERAUTH.SignUPPassengerActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginActivity extends AppCompatActivity {

    Button btn_admlogin, btn_signup, btn_admnpage;
    TextInputEditText tied_admname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        tied_admname = findViewById(R.id.tied_admname);
        btn_signup = findViewById(R.id.btn_signup);
        btn_admlogin = findViewById(R.id.btn_admlogin);

        btn_admnpage =findViewById(R.id.btn_admnpage);

        btn_admnpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
            }
        });
        btn_admlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(AdminLoginActivity.this);
                materialAlertDialogBuilder.setCancelable(false)
                        .setTitle("ALERT")
                        .setMessage("Welcome Admin")
                        .setPositiveButton("PRECEED", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                                intent.putExtra("role",tied_admname.getText().toString());
                                startActivity(intent);
                            }
                        })
                        .show();



            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, SignUPPassengerActivity.class));
            }
        });


    }
}