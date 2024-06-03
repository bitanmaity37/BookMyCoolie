package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cdac.iaf.bookmycoolie.R;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private ImageView imageView;
    private int[] imageIds = {R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4}; // Assuming image IDs are defined in drawable.xml
    private int currentImageIndex = 0;
    private Handler handler;
    private Runnable imageTransition;
    Button btn_login;
       MaterialCardView mcv_reqc;
       LinearLayout ll_tyc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView = findViewById(R.id.image_view);
        mcv_reqc = findViewById(R.id.mcv_reqc);
        ll_tyc = findViewById(R.id.ll_tyc);

        handler = new Handler();
        btn_login = findViewById(R.id.btn_login);

        imageTransition = new Runnable() {
            @Override
            public void run() {
                // Update image view with next image and handle looping/stopping
                imageView.setImageResource(imageIds[currentImageIndex]);
                currentImageIndex = (currentImageIndex + 1) % imageIds.length; // Loop back to first image if at the end

                // Schedule next image transition
                handler.postDelayed(imageTransition, 3000); // Change delay as needed (in milliseconds)
            }
        };

        handler.post(imageTransition); // Start the slideshow

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, AdminLoginActivity.class);
                startActivity(intent);

            }
        });

        mcv_reqc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(HomeActivity.this, RequestCoolieActivity.class));
            }
        });

        ll_tyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,TrackingActivity.class));
            }
        });
    }
}