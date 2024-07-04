package com.cdac.iaf.bookmycoolie.activities.OPERATOR;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.models.Coolie;
import com.cdac.iaf.bookmycoolie.utils.InvalidateUser;
import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class OpHomeActivity extends AppCompatActivity {

    MaterialCardView add_coolie, rqstcoolie, mngattndnce, listcoolies, rqstcart,rqstchair;
            Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_home);
        add_coolie = findViewById(R.id.add_coolie);
        listcoolies = findViewById(R.id.listcoolies);

        rqstcoolie = findViewById(R.id.rqstcoolie);

        mngattndnce = findViewById(R.id.mngattndnce);
        rqstcart = findViewById(R.id.rqstcart);
        rqstchair = findViewById(R.id.rqstchair);

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InvalidateUser.invalidate(OpHomeActivity.this);
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        mngattndnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(OpHomeActivity.this, CoolieAttendanceActivity.class));
            }
        });

        rqstcoolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class));
                //showPopupMenu(view);
                startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class).putExtra("serviceMode",1));

            }
        });

        rqstchair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class).putExtra("serviceMode",3));

            }
        });

        rqstcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpHomeActivity.this, AssignHomeActivity.class).putExtra("serviceMode",2));
            }
        });


        add_coolie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu2(view);

                Intent intent = new Intent(OpHomeActivity.this, AddCoolieActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedCoolie", new Coolie());
                intent.putExtra("bundle",bundle);
                intent.putExtra("serviceMode",1);
                startActivity(intent);


            }
        });

        listcoolies.setOnClickListener(new View.OnClickListener() {
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
    private void showPopupMenu2(View view) {
        PopupMenu popupMenu = new PopupMenu(OpHomeActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_coolies, popupMenu.getMenu());

        // Set a click listener for each menu item
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.item1){
                    startActivity(new Intent(OpHomeActivity.this, AddCoolieActivity.class));
                    return true;
                }
                else if (item.getItemId()==R.id.item2) {
                    startActivity(new Intent(OpHomeActivity.this, CoolieListActivity.class));
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }
    boolean doubleBackToExitPressedOnce = false;
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}