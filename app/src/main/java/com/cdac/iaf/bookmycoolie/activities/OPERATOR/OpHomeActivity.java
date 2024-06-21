package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;

public class OpHomeActivity extends AppCompatActivity {

    Button btn_addCoolie, btn_asgnCoolie, btn_modCoolie, btn_attendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_home);
        btn_addCoolie = findViewById(R.id.btn_addCoolie);
        btn_asgnCoolie = findViewById(R.id.btn_asgnCoolie);
        btn_modCoolie = findViewById(R.id.btn_modCoolie);
        btn_attendance = findViewById(R.id.btn_attendance);

        btn_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(OpHomeActivity.this, CoolieAttendanceActivity.class));
            }
        });

        btn_asgnCoolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class));
                showPopupMenu(view);
            }
        });


        btn_addCoolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpHomeActivity.this, AddCoolieActivity.class));
            }
        });

        btn_modCoolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpHomeActivity.this, CoolieListActivity.class));
            }
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(OpHomeActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

        // Set a click listener for each menu item
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.item1){
                    startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class).putExtra("callmode",1));
                    return true;
                }
                else if (item.getItemId()==R.id.item2) {
                    startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class).putExtra("callmode",2));
                    return true;
                }
                else if (item.getItemId()==R.id.item3){
                    startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class).putExtra("callmode",3));
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }
}