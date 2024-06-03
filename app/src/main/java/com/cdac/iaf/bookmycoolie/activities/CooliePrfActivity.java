package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cdac.iaf.bookmycoolie.R;

public class CooliePrfActivity extends AppCompatActivity {

    TextView tv_addc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coolie_prf);

        tv_addc = findViewById(R.id.tv_addc);
        tv_addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CooliePrfActivity.this, AddCoolieActivity.class);
                startActivity(intent);
            }
        });
    }
}