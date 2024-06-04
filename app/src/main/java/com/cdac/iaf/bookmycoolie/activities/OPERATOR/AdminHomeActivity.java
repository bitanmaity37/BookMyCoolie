package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AdminHomeActivity extends AppCompatActivity {

    Button btn_addopt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home2);

        btn_addopt = findViewById(R.id.btn_addopt);
        btn_addopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AdminHomeActivity.this);
                View view1 = LayoutInflater.from(AdminHomeActivity.this).inflate(R.layout.operator_bottom_sheet_layout,null);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                bottomSheetDialog.show();
                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(AdminHomeActivity.this, "dismissed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}