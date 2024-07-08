package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.activities.PASSENGER.PassengerHome;
import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.ADMIN.AdminHomeActivity;
import com.cdac.iaf.bookmycoolie.activities.OPERATOR.OpHomeActivity;
import com.cdac.iaf.bookmycoolie.activities.USERAUTH.SignUPPassengerActivity;
import com.cdac.iaf.bookmycoolie.models.LoginRequest;
import com.cdac.iaf.bookmycoolie.models.LoginResponse;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.NetworkUtils;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.cdac.iaf.bookmycoolie.utils.SharedPreferenceUtility;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.security.GeneralSecurityException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity {

    Button btn_admlogin, btn_signup, btn_admnpage, btn_oprtrpage, btn_psngrpage,btnLogout;
    TextInputEditText tied_admname, tied_admpwd;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;
    boolean isNetworkAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        isNetworkAvailable = NetworkUtils.isInternetAvailable(this);
        if (!isNetworkAvailable) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        tied_admname = findViewById(R.id.tied_admname);
        tied_admpwd = findViewById(R.id.tied_admpwd);
        btn_signup = findViewById(R.id.btn_signup);
        btn_admlogin = findViewById(R.id.btn_admlogin);
        btnLogout = findViewById(R.id.logout);
        btnLogout.setVisibility(View.GONE);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(AdminLoginActivity.this);
            if (securedSharedPreferenceUtils.isUserLogin()) {
                switchActivities(securedSharedPreferenceUtils.getLoginData().getRoleName());
            }
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        btn_admlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ProgressDialog progressDialog = new ProgressDialog(AdminLoginActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Please Wait!!");
                progressDialog.show();

                Call<LoginResponse> call = RestClient.getRetrofitClient()
                        .create(RestInterface.class)
                        .getAccess(new LoginRequest(tied_admname.getText().toString().trim(),
                                tied_admpwd.getText().toString().trim()));

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        progressDialog.dismiss();

                        // sharedPreferences.edit().putString("auth_token", "Bearer " + response.body().getJwtToken()).apply();
                        //   sharedPreferences.edit().putString("user_role", response.body().getRoleName()).apply();
                        if (response.code() == 200) {

                            System.out.println("Response CODE LOGIN " + response.code());
                            System.out.println("Details ++++" + response.body());
                            System.out.println("JWT: " + response.body().getJwtToken());

                            securedSharedPreferenceUtils.saveLoginData(response.body());
                            securedSharedPreferenceUtils.updateUserLoginStatus(true);
                            switchActivities(response.body().getRoleName());

                            Toast.makeText(AdminLoginActivity.this, "ROLE " + response.body().getRoleName(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AdminLoginActivity.this, "Invalid Email ID or Password ", Toast.LENGTH_LONG).show();
                        }

                        // sharedPreferenceUtility.saveToken(response.body().getJwtToken(), AdminLoginActivity.this);
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                        progressDialog.dismiss();

                        System.out.println("Login error" + t.getMessage());
                    }
                });
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, SignUPPassengerActivity.class));
            }
        });


    }

    public void switchActivities(String role) {
        switch (role) {
            case "ROLE_PASSANGER":
                Toast.makeText(AdminLoginActivity.this, "WELCOME PASSENGER", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminLoginActivity.this, PassengerHome.class));

                break;
            case "ROLE_ADMIN":
                Toast.makeText(AdminLoginActivity.this, "WELCOME ADMIN", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
                finish();
                break;
            case "ROLE_OPERATOR":
                Toast.makeText(AdminLoginActivity.this, "WELCOME OPERATOR", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminLoginActivity.this, OpHomeActivity.class));
                finish();
                break;
            default:
                Toast.makeText(AdminLoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                finish();
        }
    }
}