package com.cdac.iaf.bookmycoolie.activities.USERAUTH;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.activities.AdminLoginActivity;
import com.cdac.iaf.bookmycoolie.models.GetOtpRequestModel;
import com.cdac.iaf.bookmycoolie.models.RegisterPassengerDetailsModel;
import com.cdac.iaf.bookmycoolie.models.SimpleResponse;
import com.cdac.iaf.bookmycoolie.models.SimpleUserIDResponse;
import com.cdac.iaf.bookmycoolie.models.VerifyOtpRequestModel;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.chaos.view.PinView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUPPassengerActivity extends AppCompatActivity {

    Button getOTPButton, verifyButton, registerButton,btnLogout;
    LinearLayout ll_register, ll_otp, ll_registerdtls;

    TextInputEditText tied_phone, tied_name, tied_pwd, tied_email, tied_eid;

    PinView pinView;
    TextView tv_otptxt, resend;
    String Mobile;
    Boolean numberVerified, otpSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_uppassenger);

        numberVerified = false;
        otpSent = false;

        getOTPButton = findViewById(R.id.getOTPButton);
        verifyButton = findViewById(R.id.verifyButton);
        registerButton = findViewById(R.id.registerButton);
        btnLogout = findViewById(R.id.logout);
        tied_eid = findViewById(R.id.tied_eid);
        TextInputLayout til_eid = findViewById(R.id.til_eid);

        til_eid.setVisibility(View.GONE);
        btnLogout.setVisibility(View.GONE);


        ll_register = findViewById(R.id.ll_register);
        ll_register.setVisibility(View.VISIBLE);

        ll_otp = findViewById(R.id.ll_otp);
        ll_otp.setVisibility(View.GONE);

        ll_registerdtls = findViewById(R.id.ll_registerdtls);
        ll_registerdtls.setVisibility(View.GONE);


        tied_phone = findViewById(R.id.tied_phone);
        tied_name = findViewById(R.id.tied_name);
        tied_pwd = findViewById(R.id.tied_pwd);
        tied_email = findViewById(R.id.tied_email);

        tv_otptxt = findViewById(R.id.tv_otptxt);
        resend = findViewById(R.id.resend);

        pinView = findViewById(R.id.pin_view);

        getOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mobile = tied_phone.getText().toString().trim();
                Mobile = "6392434664";
                validateOTP();
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpSent && !numberVerified){
                    Toast.makeText(SignUPPassengerActivity.this, pinView.getText().toString(), Toast.LENGTH_SHORT).show();
                    Call<SimpleResponse> call2 = RestClient.getRetrofitClient().create(RestInterface.class)
                            .verifyOtp(new VerifyOtpRequestModel(Mobile,pinView.getText().toString().trim()));
                    call2.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            System.out.println("res  "+response.code());

                            if(response.code() == 200){
                                Toast.makeText(SignUPPassengerActivity.this, "OTP Verified", Toast.LENGTH_LONG).show();
                                ll_register.setVisibility(View.GONE);
                                ll_otp.setVisibility(View.GONE);
                                ll_registerdtls.setVisibility(View.VISIBLE);
                                numberVerified = true;
                            }
                            if(response.code() == 401){
                                tv_otptxt.setText("WRONG OTP ENTERED, TRY AGAIN");
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            System.out.println("In Otp error"+t.getMessage());

                        }
                    });
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpSent && numberVerified){
                    Call<SimpleUserIDResponse> call3 = RestClient.getRetrofitClient().create(RestInterface.class)
                            .registerPsngr(new RegisterPassengerDetailsModel(tied_email.getText().toString().trim(),
                                    "344500",
                                    true,
                                    Mobile,
                                    tied_name.getText().toString(),
                                    tied_pwd.getText().toString(),
                                    1,
                                    4));
                    call3.enqueue(new Callback<SimpleUserIDResponse>() {
                        @Override
                        public void onResponse(Call<SimpleUserIDResponse> call, Response<SimpleUserIDResponse> response) {
                            if(response.code()==200){
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(SignUPPassengerActivity.this);
                                materialAlertDialogBuilder.setCancelable(false)
                                        .setTitle("DONE")
                                        .setMessage("USER PROFILE CREATED WITH PASSENGER ID "+response.body().getUserId())
                                        .setNegativeButton("LOGIN", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                startActivity(new Intent(SignUPPassengerActivity.this, AdminLoginActivity.class));
                                                dialogInterface.dismiss();
                                                finishAffinity();

                                            }
                                        }).show();
                            }
                            if(response.code()==401){
                                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(SignUPPassengerActivity.this);
                                materialAlertDialogBuilder.setCancelable(false)
                                        .setTitle("ERROR")
                                        .setMessage("USER PROFILE COULD NOT BE CREATED")
                                        .setNegativeButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                startActivity(new Intent(SignUPPassengerActivity.this, AdminLoginActivity.class));
                                                dialogInterface.dismiss();
                                                finishAffinity();

                                            }
                                        }).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<SimpleUserIDResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private void validateOTP() {

        System.out.println("before call"+Mobile);
        Call<SimpleResponse> call = RestClient.getRetrofitClient().create(RestInterface.class).getOTP(new GetOtpRequestModel(Mobile));
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {

                System.out.println("Response "+response.code());

                if(response.code() == 200){
                    Toast.makeText(SignUPPassengerActivity.this, "OTP SENT SUCCESSFULLY TO "+Mobile, Toast.LENGTH_LONG).show();

                    ll_register.setVisibility((View.GONE));
                    ll_otp.setVisibility(View.VISIBLE);
                    ll_registerdtls.setVisibility(View.GONE);
                    tv_otptxt.setText("Enter OTP for "+Mobile);
                    otpSent = true;


                    /** OTP TIMER STARTS**/
                    long timerDuration = 20000; // 2 minutes
                    new CountDownTimer(timerDuration, 1000) { // milliseconds between updates (1 second)
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Convert milliseconds to minutes and seconds format
                            long minutes = (millisUntilFinished / 1000) / 60;
                            long seconds = (millisUntilFinished / 1000) % 60;
                            // Format time string with leading zeros for minutes and seconds
                            String timeLeft = String.format("%02d:%02d", minutes, seconds);
                            resend.setText(timeLeft);
                        }
                        @Override
                        public void onFinish() {
                            resend.setText("Re-send OTP");
                            resend.setClickable(true); // Make TextView clickable
                            resend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Show toast message on click
                                    Toast.makeText(getApplicationContext(), "Re-sending OTP...", Toast.LENGTH_SHORT).show();
                                    validateOTP();
                                    // You can potentially reset the timer here (optional)
                                    // countdownTextView.setClickable(false);
                                    // new CountDownTimer(...).start();
                                }
                            });
                        }
                    }.start();
                    /** OTP TIMER STARTS**/
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                System.out.println("On error "+t.getMessage());

            }
        });




    }
}