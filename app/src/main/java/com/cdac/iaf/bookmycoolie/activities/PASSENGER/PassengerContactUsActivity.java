package com.cdac.iaf.bookmycoolie.activities.PASSENGER;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cdac.iaf.bookmycoolie.R;
import com.cdac.iaf.bookmycoolie.adpater.ContactUsAdapter;
import com.cdac.iaf.bookmycoolie.models.ContactUsModel;
import com.cdac.iaf.bookmycoolie.models.Operator;
import com.cdac.iaf.bookmycoolie.restapi.RestClient;
import com.cdac.iaf.bookmycoolie.restapi.RestInterface;
import com.cdac.iaf.bookmycoolie.utils.SecuredSharedPreferenceUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerContactUsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Operator> contactList = new ArrayList<>();
    String authToken;
    SecuredSharedPreferenceUtils securedSharedPreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_contact_us);

        try {
            securedSharedPreferenceUtils = new SecuredSharedPreferenceUtils(PassengerContactUsActivity.this);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authToken = securedSharedPreferenceUtils.getLoginData().getJwtToken();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Handle profile item click
                startActivity(new Intent(PassengerContactUsActivity.this, PassengerHome.class));
                Toast.makeText(PassengerContactUsActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.faq_item) {
                // Handle FAQ item click
                startActivity(new Intent(PassengerContactUsActivity.this, PassengerFaqActivity.class));
                Toast.makeText(PassengerContactUsActivity.this, "FAQ Clicked", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.contact_item) {
                // Handle contact item click

                startActivity(new Intent(PassengerContactUsActivity.this, PassengerContactUsActivity.class));
                Toast.makeText(PassengerContactUsActivity.this, "Contact Us Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        TextView navbarTitle = findViewById(R.id.navbar_title);
        navbarTitle.setText(R.string.passenger_contact);

        Call<ArrayList<Operator>> getOperatorListCall = RestClient.getRetrofitClient().create(RestInterface.class)
                .getOperators(authToken);

        getOperatorListCall.enqueue(new Callback<ArrayList<Operator>>() {
            @Override
            public void onResponse(Call<ArrayList<Operator>> call, Response<ArrayList<Operator>> response) {
                contactList = response.body();
                recyclerView = findViewById(R.id.contact_us_recycler_view);
                ContactUsAdapter adapter = new ContactUsAdapter(PassengerContactUsActivity.this,contactList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Operator>> call, Throwable t) {

            }
        });


    }
}