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

    Button btn_admlogin, btn_signup, btn_admnpage, btn_oprtrpage, btn_psngrpage;
    TextInputEditText tied_admname, tied_admpwd;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        tied_admname = findViewById(R.id.tied_admname);
        tied_admpwd = findViewById(R.id.tied_admpwd);
        btn_signup = findViewById(R.id.btn_signup);
        btn_admlogin = findViewById(R.id.btn_admlogin);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(AdminLoginActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        /*btn_oprtrpage = findViewById(R.id.btn_oprtrpage);
        btn_psngrpage = findViewById(R.id.btn_psngrpage);

        btn_admnpage = findViewById(R.id.btn_admnpage);*/

        // btn_psngrpage.setOnClickListener(v -> startActivity(new Intent(AdminLoginActivity.this, PassengerHome.class)));

        /*btn_admnpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
            }
        });*/

        /*btn_oprtrpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminLoginActivity.this, OpHomeActivity.class));
            }
        });*/
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
                        System.out.println("Response CODE LOGIN " + response.code());
                        System.out.println("Details ++++" + response.body().toString());
                        System.out.println("JWT: " + response.body().getJwtToken());

                        progressDialog.dismiss();

                       // sharedPreferences.edit().putString("auth_token", "Bearer " + response.body().getJwtToken()).apply();
                     //   sharedPreferences.edit().putString("user_role", response.body().getRoleName()).apply();
                        if(response.code()==200){

                            securedSharedPreferenceUtils.saveLoginData(response.body());
                            switch (response.body().getRoleName()) {
                                case "ROLE_PASSANGER":
                                    startActivity(new Intent(AdminLoginActivity.this, PassengerHome.class));
                                    break;
                                case "ROLE_ADMIN":
                                    startActivity(new Intent(AdminLoginActivity.this, AdminHomeActivity.class));
                                    break;
                                case "ROLE_OPERATOR":
                                    startActivity(new Intent(AdminLoginActivity.this, OpHomeActivity.class));
                                    break;
                                default:
                                    Toast.makeText(AdminLoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }



                        // sharedPreferenceUtility.saveToken(response.body().getJwtToken(), AdminLoginActivity.this);

                        Toast.makeText(AdminLoginActivity.this, "ROLE " + response.body().getRoleName(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                        progressDialog.dismiss();

                        System.out.println("Login error" + t.getMessage());

                    }
                });

                                /*Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                                intent.putExtra("role",tied_admname.getText().toString());
                                startActivity(intent);*/


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