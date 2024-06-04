package com.cdac.iaf.bookmycoolie.activities.USERAUTH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;

public class SignUPPassengerActivity extends AppCompatActivity {

    Button getOTPButton,
    loginButton;

    LinearLayout ll_register, ll_otp;
    TextView resend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_uppassenger);

        getOTPButton = findViewById(R.id.getOTPButton);
                loginButton = findViewById(R.id.loginButton);
        ll_register = findViewById(R.id.ll_register);
                ll_otp = findViewById(R.id.ll_otp);
        resend = findViewById(R.id.resend);
        ll_otp.setVisibility(View.GONE);

        getOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateOTP();



            }
        });
    }

    private void validateOTP() {
        ll_register.setVisibility(View.GONE);
        ll_otp.setVisibility(View.VISIBLE);

        long timerDuration = 30000; // 2 minutes

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

    }
}