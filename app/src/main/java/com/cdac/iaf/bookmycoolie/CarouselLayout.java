package com.cdac.iaf.bookmycoolie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class CarouselLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel_layout);

        TextView imageView = findViewById(R.id.station_name);

       // Glide.with(CarouselLayout.this).load(getIntent().getStringExtra("image")).into(imageView);

    }
}