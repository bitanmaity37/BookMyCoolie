package com.cdac.iaf.bookmycoolie.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cdac.iaf.bookmycoolie.R;
import com.google.android.material.card.MaterialCardView;

public class AdminHomeActivity extends AppCompatActivity {

    MaterialCardView mcv_addadmin, mcv_cprf, mcv_assign, mcv_rprts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        mcv_addadmin = findViewById(R.id.mcv_addadmin);
                mcv_cprf = findViewById(R.id.mcv_cprf);
        mcv_assign = findViewById(R.id.mcv_assign);
                mcv_rprts = findViewById(R.id.mcv_rprts);
        mcv_addadmin.setVisibility(View.GONE);
        /*mcv_cprf.setVisibility(View.GONE);
        mcv_assign.setVisibility(View.GONE);
        mcv_rprts.setVisibility(View.GONE);*/



        Intent intent = getIntent();
        System.out.println("In Admin Home "+intent.getStringExtra("role"));
        String role = intent.getStringExtra("role");

        /*switch (role){
            case "admin":
                mcv_addadmin.setVisibility(View.GONE);
                mcv_cprf.setVisibility(View.VISIBLE);
                mcv_assign.setVisibility(View.VISIBLE);
                mcv_rprts.setVisibility(View.VISIBLE);
                break;
            case "superadmin":
                mcv_addadmin.setVisibility(View.VISIBLE);
                mcv_cprf.setVisibility(View.GONE);
                mcv_assign.setVisibility(View.GONE);
                mcv_rprts.setVisibility(View.VISIBLE);
                break;
        }*/



        mcv_cprf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(AdminHomeActivity.this, CooliePrfActivity.class));
            }
        });

        mcv_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(AdminHomeActivity.this, AssignmentActivity.class));
            }
        });
    }
}