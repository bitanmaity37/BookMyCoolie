package com.cdac.iaf.bookmycoolie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

public class CarouselLayout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel_layout);

        ImageView imageView = findViewById(R.id.list_item_image);

        Glide.with(CarouselLayout.this).load(getIntent().getStringExtra("image")).into(imageView);

    }
}