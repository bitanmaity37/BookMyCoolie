package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cdac.iaf.bookmycoolie.R;

public class OpHomeActivity extends AppCompatActivity {

    Button btn_addCoolie, btn_asgnCoolie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_home);
        btn_addCoolie = findViewById(R.id.btn_addCoolie);
        btn_asgnCoolie = findViewById(R.id.btn_asgnCoolie);

        btn_asgnCoolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class));
            }
        });


        btn_addCoolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpHomeActivity.this, AddCoolieActivity.class));
            }
        });
    }
}